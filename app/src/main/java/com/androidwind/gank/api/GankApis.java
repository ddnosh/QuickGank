package com.androidwind.gank.api;

import com.androidwind.gank.bean.model.FullGank;
import com.androidwind.gank.bean.model.SearchGank;
import com.androidwind.gank.bean.model.SimpleGank;
import com.androidwind.gank.constant.Constants;

import io.reactivex.Observable;
import retrofit2.http.GET;
import retrofit2.http.Path;

/**
 * @author ddnosh
 * @website http://blog.csdn.net/ddnosh
 */
public interface GankApis {

    //for gank list
    @GET(value = "data/{catalogue}/" + Constants.CATALOGUE_SIZE + "/{page}")
    Observable<SimpleGank> getGankList(@Path("catalogue") String catalogue, @Path("page") int page);

    //for gank daily
    @GET("day/{year}/{month}/{day}")
    Observable<FullGank> getGankDaily(@Path("year") int year, @Path("month") int month, @Path("day") int day);

    //for gank search
    @GET("search/query/{search}/category/all/count/" + Constants.CATALOGUE_SIZE + "/page" + "/{page}")
    Observable<SearchGank> getGankSearch(@Path("search") String search, @Path("page") int page);

}
