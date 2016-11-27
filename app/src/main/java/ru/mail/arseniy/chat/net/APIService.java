package ru.mail.arseniy.chat.net;

/**
 * Класс для работы с http запросами
 */

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;
import ru.mail.arseniy.chat.User;

public interface APIService {

    /**
     * Создаем нового пользователя
     * @param username
     * @param cb
     */
    @POST("/users/{username}")
    Call<User> addUser(@Path("username") String username, Callback<User> cb);

    /**
     * Проверяем есть ли пользователь с таким именем и паролем
     * @return
     */
    @GET("/users/{username}")
    Call<Boolean> isUserExists(@Query("login") String login, @Query("password") String password);

    /**
     * Получаем список пользователей с полями (login,name,open_key,status)
     * @return
     */
    @GET("/users")
    Call<List<User>> getUsers();

    /**
     * Отправляем сообщение пользователю
     * @param message
     * @param login
     */
    @POST("/users/{username}/message")
    void sendMessage(@Query("message") String message, @Query("login") String login);


}
