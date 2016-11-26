package ru.mail.arseniy.chat.net;

/**
 * Класс для работы с http запросами
 */

import retrofit2.Callback;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.Response;
import retrofit2.http.Path;
import ru.mail.arseniy.chat.User;

public interface APIService {

    /**
     * Созжаем нового юзера
     * @param username
     * @param cb
     */
    @POST("/users/{username}")
    void addUser(@Path("username") String username, Callback<User> cb);

    /**
     * Проверяем есть ли пользователь с таким именем и паролем
     * @return
     */
    @GET("/users/{username}")
    Response getUser();

    /**
     * Получаем список пользователей
     * @return
     */
    @GET("/users")
    Response getUsers();

    /**
     * Отправляем сообщение пользователю
     */
    @POST("/users/{username}/message")
    void sendMessage();


}
