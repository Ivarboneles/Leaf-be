package com.example.leaf.utils;

import com.example.leaf.dto.response.DataResponse;
import com.example.leaf.dto.response.ListResponse;
import com.example.leaf.entities.ModelAI;
import com.example.leaf.repositories.UserRepository;
import org.apache.mahout.cf.taste.common.TasteException;
import org.apache.mahout.cf.taste.common.Weighting;
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
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
public class ServiceUtils {
    @Autowired
    ModelMapper mapper;

    @Autowired
    UserRepository userRepository;

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

    public void recomendUser(String username) throws TasteException {
        List<ModelAI> list = userRepository.getDataSourceForAI();
        List<String> listUserName = new ArrayList<>();
        List<String> listItemName = new ArrayList<>();
        System.out.println("get list");
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

        System.out.println("create list Username "+listUserName.size());
        for(String id: listUserName){
            System.out.println(id);
        }


        System.out.println("create list Item name "+ listItemName.size());
        for(String id: listItemName){
            System.out.println(id);
        }

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
        System.out.println("create list preference");
        System.out.println(preferenceMap);

        DataModel model = new GenericDataModel(preferenceMap);
        System.out.println("create model");
        System.out.println(model);
        // Create user similarity matrix
        UserSimilarity similarity = new PearsonCorrelationSimilarity(model);
        System.out.println("create similarity");
        System.out.println(similarity);
        // Create user neighborhood
        UserNeighborhood neighborhood = new NearestNUserNeighborhood(20, 0.3, similarity, model, 1);
        System.out.println("create neighborhood");
        System.out.println(neighborhood);
        // Create recommender
        GenericUserBasedRecommender recommender = new GenericUserBasedRecommender(model, neighborhood, similarity);
        System.out.println("create recommender");
        System.out.println(recommender);
        // Get recommendations for user
        List<RecommendedItem> recommendations = recommender.recommend(Long.valueOf(listUserName.indexOf(username)), 20);
        System.out.println("create recommendations");
        System.out.println(recommendations);
        // Print recommendations
        for (RecommendedItem recommendation : recommendations) {
            System.out.println(listItemName.get(Math.toIntExact(recommendation.getItemID())));
        }
    }
}
