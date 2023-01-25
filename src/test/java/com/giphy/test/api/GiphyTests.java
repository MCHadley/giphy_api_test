package com.giphy.test.api;


import com.giphy.test.category.ApiTests;
import org.junit.Test;
import org.junit.experimental.categories.Category;
import org.springframework.boot.test.context.SpringBootTest;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.*;

@Category(ApiTests.class)
@SpringBootTest
public class GiphyTests {

    private static final String API_KEY = "YbclLj2GOvCpPKYbqZibbM8N14BoufIt";
    private static final String BAD_API_KEY = "XXXXXXXXXXXXXXXXXXXXXXXXXXXX";
    private static final String searchEndpoint = "http://api.giphy.com/v1/stickers/search";
    private static final String trendingGifUrl = "http://api.giphy.com/v1/gifs/trending";
    private static final String trendingStickerUrl = "http://api.giphy.com/v1/stickers/trending";

    //GIF Tests
    @Test
    public void getGifByIdReturnsGif(){
        given().
                param("api_key", API_KEY).
        when().
                get("http://api.giphy.com/v1/gifs/zdIGTIdD1mi4").
        then().
                statusCode(200)
                .body("data.type", equalTo("gif"))
                .body("data.id", is("zdIGTIdD1mi4"))
                .body("data.rating", is("g"))
                .body("meta.msg", is("OK"));
    }

    @Test
    public void getGifByIdNoAPIKey(){
        when().
                get("http://api.giphy.com/v1/gifs/zdIGTIdD1mi4").
        then().
                statusCode(401)
                .body("meta.msg", equalTo("No API key found in request."));
    }

    @Test
    public void getGifByIdBadAPIKey(){
        given().
                param("api_key", BAD_API_KEY).
        when().
                get("http://api.giphy.com/v1/gifs/zdIGTIdD1mi4").
        then().
                statusCode(401)
                .body("meta.msg", equalTo("Unauthorized"));
    }

    @Test
    public void getGifByIdNotFound(){
        given().
                param("api_key", API_KEY).
        when().
                get("http://api.giphy.com/v1/gifs/zdIGTIdD1mi4XXXX").
        then().
                statusCode(404)
                .body("meta.msg", equalTo("Not Found"));
    }

    @Test
    public void getSearchStickerEndpoint(){
        given().
                param("api_key", API_KEY).param("q", "baseball").param("limit", 1).
        when().
                get(searchEndpoint).
        then().
                statusCode(200)
                .body("data[0].type", equalTo("sticker"))
                .body("data[0].id", is("3ohzdJKvFq7VYRhKhy"))
                .body("data[0].rating", is("g"))
                .body("meta.msg", is("OK"));
    }

    @Test
    public void getSearchStickerNoApiKey(){
        when()
                .get(searchEndpoint).
        then().
                statusCode(401)
                .body("meta.msg", equalTo("No API key found in request."));
    }

    @Test
    public void getSearchStickerBadApiKey(){
        given().
                param("api_key", BAD_API_KEY).
        when().
                get(searchEndpoint).
        then().
                statusCode(401)
                .body("meta.msg", equalTo("Unauthorized"));
    }

    @Test
    public void getSearchStickerByNoResults(){
        given().
                param("api_key", API_KEY).
        when().
                get(searchEndpoint).
        then().
                statusCode(200)
                .body("pagination.total_count", equalTo(0))
                .body("meta.msg", is("OK"));
    }

    @Test
    public void postStickerSearch(){
        given().
                param("api_key", API_KEY).
        when().
                post(searchEndpoint).
        then().
                statusCode(404)
                .body("meta.msg", is("Not Found!"));
    }

    @Test
    public void getStickerSearch(){
        given().
                param("api_key", API_KEY).
        when().
                options(searchEndpoint).
        then().
                statusCode(204);
    }

    @Test
    public void deleteStickerSearch(){
        given().
                param("api_key", API_KEY).
        when().
                delete(searchEndpoint).
        then().
                statusCode(404)
                .body("meta.msg", equalTo("Not Found!"));
    }

    @Test
    public void getTrendingGifs(){
        given().
                param("api_key", API_KEY).
        when().
                get(trendingGifUrl).
        then().
                statusCode(200)
                .body("meta.msg", equalTo("OK"));
    }

    @Test
    public void getTrendingGifsNoApiKey(){
        when().
                get(trendingGifUrl).
        then().
                statusCode(401)
                .body("meta.msg", equalTo("No API key found in request."));
    }

    @Test
    public void getTrendingGifsBadApiKey(){
        given().
                param("api_key", BAD_API_KEY).
        when().
                get(trendingGifUrl).
        then().
                statusCode(401)
                .body("meta.msg", equalTo("Unauthorized"));
    }

    @Test
    public void postTrendingGifs(){
        given().
                param("api_key", API_KEY).
        when().
                post(trendingGifUrl).
        then().
                statusCode(403)
                .body("message", equalTo("You cannot consume this service"));
    }

    @Test
    public void deleteTrendingGifs(){
        given().
                param("api_key", API_KEY).
        when().
                delete(trendingGifUrl).
        then().
                statusCode(403)
                .body("message", equalTo("You cannot consume this service"));
    }

    @Test
    public void optionsTrendingGifs(){
        given().
                param("api_key", API_KEY).
        when().
                options(trendingGifUrl).
        then().
                statusCode(204);
    }
}
