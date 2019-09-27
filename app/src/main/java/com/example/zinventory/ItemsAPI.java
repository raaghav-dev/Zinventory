package com.example.zinventory;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ItemsAPI {

    @GET("api/items")
    Call<ItemList> getAllItems();

}
