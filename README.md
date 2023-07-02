# ProjectS7
1. Клонируем проект на локальную машину.   
   Для запуска БД необходим Докер:
2. Запускаем команду cmd из папки проекта.
3. Пишем команду docker-compose up.   
4. Интегрируем проект в Идею.
5. Ссылка на свагер: http://localhost:8090/projectS7/swagger-ui/index.html#/   
   Описание ЭндПойнтов:
   
/projectS7/api/AddFriend: принимает токен, который генерирует метод (/projectS7/api/Authorization) и id пользователя, которого мы добавляем в друзья.

/projectS7/api/Authorization: принимает тело с логинов и поролем, если пользователь есть в БД отдает токен (действителен сутки)

/projectS7/api/CreateUser: принимает тело с логинов и поролем, если пользователя нет в БД создает его.

/projectS7/api/DeleteFriend: принимает токен, который генерирует метод (/projectS7/api/Authorization) и id пользователя, которого мы удаляем из друзья.

/projectS7/api/findPersonByUsername:  принимает токен, который генерирует метод (/projectS7/api/Authorization) и id пользователя, осуществляет поиск пользователя по БД.

/projectS7/api/GetAllFriends:  принимает токен, который генерирует метод (/projectS7/api/Authorization) и выводит список друзей пользователя.

/projectS7/api/GetUsers: выводит список всех пользователей.

/projectS7/api/LoadUserByUsername: принимает имя пользователя и выводит по нему всю информацию.
