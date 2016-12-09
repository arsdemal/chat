# chat

Запросы на сервер

Запрос на регистрацию
```
{
    "action":"register",
    "data":{
        "login":"LOGIN",
        "pass":"PASS"
    }
}
```
Запрос на авторизацию
```
{
    "action":"auth",
    "data":{
        "login":"LOGIN",
        "pass":"PASS"
    }
}
```
Запрос на список клиентов

Отправка сообщения юзеру
```
{
    "action":"send",
    "data":{
        "login":"LOGIN",
        "pass":"PASS"
    }
}
```
Ответы от сервера

Ответ на авторизацию/регистрацию пользователя
```
{
    "action":"welcome",
    "status":"STATUS_TYPE"
}
```
Типы ошибок
```
STATUS_TYPE {
    AUTH_OK,
    AUTH_FAILED,
    BAD_PASS_OR_LOGIN,
    USER_EXIST
}
```

