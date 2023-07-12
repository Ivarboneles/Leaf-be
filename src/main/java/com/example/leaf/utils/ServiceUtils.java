package com.example.leaf.utils;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.dto.response.SearchUserResponseDTO;
import com.example.leaf.dto.response.UserResponseDTO;
import com.example.leaf.entities.enums.RelationEnum;
import com.example.leaf.repositories.model.ModelAI;
import com.example.leaf.entities.RelationShip;
import com.example.leaf.entities.User;
import com.example.leaf.entities.keys.RelationShipKey;
import com.example.leaf.repositories.RelationShipRepository;
import com.example.leaf.repositories.UserRepository;
import com.example.leaf.repositories.model.ModelCommonFriend;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.impl.common.FastByIDMap;
import org.apache.mahout.cf.taste.impl.model.GenericDataModel;
import org.apache.mahout.cf.taste.impl.model.GenericUserPreferenceArray;
import org.apache.mahout.cf.taste.impl.neighborhood.NearestNUserNeighborhood;
import org.apache.mahout.cf.taste.impl.recommender.GenericUserBasedRecommender;
import org.apache.mahout.cf.taste.impl.similarity.PearsonCorrelationSimilarity;
import org.apache.mahout.cf.taste.model.DataModel;
import org.apache.mahout.cf.taste.model.PreferenceArray;
import org.apache.mahout.cf.taste.neighborhood.UserNeighborhood;
import org.apache.mahout.cf.taste.recommender.RecommendedItem;
import org.apache.mahout.cf.taste.similarity.UserSimilarity;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class ServiceUtils {
    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

    @Autowired
    RelationShipRepository relationShipRepository;

    public <T, V> ListResponse<V> convertToListResponse(List<T> src, Class<V> cls) {
        return new ListResponse<>(src.stream().map(p -> mapper.map(p, cls)).toList());
    }
    public <T, V> DataResponse<V> convertToDataResponse(T src, Class<V> cls) {
        return new DataResponse<>(mapper.map(src, cls));
    }

    public <T, V> V convertToResponseDTO(T src, Class<V> cls) {
        return mapper.map(src, cls);
    }

    public String GenerateID (){
        return UUID.randomUUID().toString();
    }

    public List<SearchUserResponseDTO> recomendFriend(User user) throws TasteException {
        List<ModelAI> list = userRepository.getDataSourceForAI(200);
        List<String> listUserName = new ArrayList<>();
        List<String> listItemName = new ArrayList<>();

        listItemName.add("A");
        listUserName.add("A");
        for(ModelAI item : list ){
            if(!listUserName.contains(item.getUser_id())){
                listUserName.add(item.getUser_id());
            }

            if(!listItemName.contains(item.getItem_id())){
                listItemName.add(item.getItem_id());
            }
        }

        FastByIDMap<PreferenceArray> preferenceMap = convertListToFastByIDMap(list, listItemName, listUserName);

        List<RecommendedItem> recommendations = recommendedItems(preferenceMap, listUserName, user);

        // Print recommendations
        List<SearchUserResponseDTO> userList = new ArrayList<>();
        for (RecommendedItem recommendation : recommendations) {
            String recommendUsername = listItemName.get(Math.toIntExact(recommendation.getItemID()));

            Boolean flag = true;

            if (user.getUsername().equals(recommendUsername)){
                flag = false;
            }

            if(flag) {
                Optional<RelationShip> relationShipOptional1 = relationShipRepository.findById(
                        new RelationShipKey(user.getUsername(), recommendUsername)
                );

                if (relationShipOptional1.isPresent()) {
                    if (relationShipOptional1.get().getStatus().equals("BLOCK") || relationShipOptional1.get().getStatus().equals("FRIEND")) {
                        flag = false;
                    }
                }

                Optional<RelationShip> relationShipOptional2 = relationShipRepository.findById(
                        new RelationShipKey(recommendUsername, user.getUsername())
                );

                if (relationShipOptional2.isPresent()) {
                    if (relationShipOptional2.get().getStatus().equals("BLOCK") || relationShipOptional2.get().getStatus().equals("FRIEND")) {
                        flag = false;
                    }
                }
            }

            if(flag){
                Optional<User> recommendUserOpt = userRepository.findUserByUsername(recommendUsername);
                if(recommendUserOpt.isPresent()){
                    SearchUserResponseDTO userResponseDTO = convertToResponseDTO(recommendUserOpt.get(), SearchUserResponseDTO.class);
                    List<ModelCommonFriend> commonFriendList = userRepository.getCommonFriend(user.getUsername(), recommendUsername);

                    for(ModelCommonFriend item : commonFriendList){
                        userResponseDTO.setCountCommonFriend(item.getCommon());
                    }
                    userList.add(userResponseDTO);
                }
            }
        }
        if(userList.isEmpty()){
           List<User> listUser = userRepository.findAll( PageRequest.of(0, 10).withSort(Sort.by("createDate").descending())).stream().toList();
           for(User item : listUser){
               userList.add(convertToResponseDTO(item, SearchUserResponseDTO.class));
           }
        }
        return userList;
    }

    public FastByIDMap<PreferenceArray> convertListToFastByIDMap(List<ModelAI> list, List<String> listItemName, List<String> listUserName){
        FastByIDMap<PreferenceArray> preferenceMap = new FastByIDMap<>();
        for (ModelAI item : list) {
            long userID = Long.valueOf(listUserName.indexOf(item.getUser_id()));
            long itemID = Long.valueOf(listItemName.indexOf(item.getItem_id()));
            float value = item.getRating();

            PreferenceArray prefsArray = preferenceMap.get(userID);
            if (prefsArray == null) {
                prefsArray = new GenericUserPreferenceArray(listItemName.size()); // tạo mới PreferenceArray nếu chưa có trong preferenceMap
            }
            prefsArray.setUserID(listItemName.indexOf(item.getItem_id()), userID);
            prefsArray.setItemID(listItemName.indexOf(item.getItem_id()), itemID);
            prefsArray.setValue(listItemName.indexOf(item.getItem_id()), value);
            preferenceMap.put(userID, prefsArray);   // đưa PreferenceArray vào preferenceMap
        }

        return  preferenceMap;
    }

    public List<RecommendedItem> recommendedItems (FastByIDMap<PreferenceArray> preferenceMap, List<String> listUserName, User user) throws TasteException {
        List<RelationShip> relationShipList = relationShipRepository.findAllByUserFromOrUserToAndStatus(user, user, RelationEnum.FRIEND.toString());
        Integer size = relationShipList.size() + 50;
        DataModel model = new GenericDataModel(preferenceMap);

        // Create user similarity matrix
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);

        // Create user neighborhood
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(size, 0.1, similarity, model, 1);

        // Create recommender
        GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);

        // Get recommendations for user
        List<RecommendedItem> recommendations = recommender.recommend(Long.valueOf(listUserName.indexOf(user.getUsername())), size);

        return recommendations;
    }
}
