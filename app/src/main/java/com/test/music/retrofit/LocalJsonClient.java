package com.test.music.retrofit;

import java.io.IOException;
import java.util.Collections;

import retrofit.client.Client;
import retrofit.client.Request;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;

public class LocalJsonClient implements Client {

    @Override
    public Response execute(Request request) throws IOException {
        String json = "{\n" +
                "   \"artists\":[\n" +
                "      {\n" +
                "         \"id\":1,\n" +
                "         \"name\":\"Inioth\",\n" +
                "         \"description\":\"Lorem ipsum dolor sit amet, consectetur adipiscing elit. Etiam varius nunc quis est ullamcorper, sed vestibulum enim egestas. Nulla nunc ante, cursus vitae leo nec, mattis viverra dolor. Suspendisse lobortis ipsum id dolor pretium ultricies. Sed ac neque sed mauris fringilla accumsan ac et velit. Aliquam rhoncus, turpis sed semper mollis, neque purus cursus nisl, tempor vehicula ligula quam id arcu. Cras eros diam, commodo id placerat eu, iaculis nec nisl. In vestibulum rhoncus vulputate. Mauris pharetra consequat tristique. Vestibulum a ante a justo consectetur feugiat nec et velit. Pellentesque suscipit massa eget tortor scelerisque bibendum. Vivamus eu pellentesque metus. Proin faucibus justo est, ut ultrices mauris ultrices ac. Vestibulum ante ipsum primis in faucibus orci luctus et ultrices posuere cubilia Curae;\",\n" +
                "         \"genres\":\"pop,rock\"\n" +
                "      }\n" +
                "   ],\n" +
                "   \"albums\":[\n" +
                "      {\n" +
                "         \"id\":1,\n" +
                "         \"artistId\":1,\n" +
                "         \"title\":\"Cour Plunder\",\n" +
                "         \"type\":\"single\"\n" +
                "      }\n" +
                "   ]\n" +
                "}";


        return new Response(request.getUrl(), 200, "nothing", Collections.EMPTY_LIST, new TypedByteArray("application/json", json.getBytes()));
    }
}
