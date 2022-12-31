package com.example.leaf.config;

import com.example.leaf.dto.response.*;
import com.example.leaf.entities.*;
import org.modelmapper.Converter;
import org.modelmapper.ModelMapper;
import org.modelmapper.convention.MatchingStrategies;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.List;

@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper() {
        ModelMapper modelMapper = new ModelMapper();
        modelMapper.getConfiguration()
                .setMatchingStrategy(MatchingStrategies.STRICT);
        //Mapper list
        var listCommentOfPost = generateListConverter(Comment.class, CommentOfPostResponseDTO.class, modelMapper);
        var listFileOfPost = generateListConverter(File.class, FileOfPostResponseDTO.class, modelMapper);
        var listReactionOfPost = generateListConverter(ReactionPost.class, ReactionPostResponseDTO.class, modelMapper);

        //Mapper Post -> PostResponseDTO
        modelMapper.createTypeMap(Post.class, PostResponseDTO.class).addMappings(m -> {
            m.using(listCommentOfPost).map(Post::getComments, PostResponseDTO::setComments);
            m.using(listFileOfPost).map(Post::getFiles, PostResponseDTO::setFiles);
            m.using(listReactionOfPost).map(Post::getReactions, PostResponseDTO::setReactions);
            m.map(Post::getId, PostResponseDTO::setId);
            m.map(Post::getUser, PostResponseDTO::setUser);
            m.map(Post::getPost, PostResponseDTO::setPost);
            m.map(Post::getCreateDate, PostResponseDTO::setCreateDate);
            m.map(Post::getValue, PostResponseDTO::setValue);
            m.map(Post::getStatus, PostResponseDTO::setStatus);
        });
        //Mapper Comment -> CommentFatherResponseDTO
        modelMapper.createTypeMap(Comment.class, CommentFatherResponseDTO.class).addMappings( m -> {
            m.map(Comment::getId, CommentFatherResponseDTO::setId);
            m.map(Comment::getUser, CommentFatherResponseDTO::setUser);
            m.map(Comment::getValue, CommentFatherResponseDTO::setValue);
            m.map(Comment::getCreateDate, CommentFatherResponseDTO::setCreateDate);
            m.map(Comment::getType, CommentFatherResponseDTO::setType);
            m.map(Comment::getStatus, CommentFatherResponseDTO::setStatus);
        });

        return modelMapper;
    }

    private <T,V> Converter<List<T>, List<V>> generateListConverter(Class<T> src, Class<V> dst, ModelMapper mapper) {
        return c -> {
            if (c.getSource() == null)
                return null;
            else
                return c.getSource().stream().map(m -> mapper.map(m, dst)).toList();
        };
    }
}
