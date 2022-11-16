# user_soap
SOAP backend для веб-приложения. Основная задача бекенда - управление пользователями и их ролями. Описание модели данных:  

У пользователя может быть несколько ролей, одна роль может быть у нескольких пользователей. Например, Вася - Админ и Оператор, Петя - Оператор и Аналитик. 
Атрибуты пользователя - Имя, Логин (первичный ключ), Пароль (шифровать пароль в рамках тестового задания не требуется, это просто строка). 
Атрибуты роли – id (первичный ключ), Имя. 
 

Необходимо: 

Разработать SOAP сервис и методы работы с данными. Сделать методы, которые будут:
Получать список пользователей из БД (без ролей)
Получать конкретного пользователя (с его ролями) из БД
Удалять пользователя в БД
Добавлять нового пользователя с ролями в БД. 
Редактировать существующего пользователя в БД. Если в запросе на редактирование передан массив ролей, система должна обновить список ролей пользователя в БД - новые привязки добавить, неактуальные привязки удалить.
На бекенде для методов добавления и редактирования должен производиться формато-логический контроль пришедших значений. Поля name, login, password - обязательные для заполнения, password содержит букву в заглавном регистре и цифру. 
Если все проверки пройдены успешно, ответ должен содержать <success>true</success>
Если случилась ошибка валидации, ответ должен содержать <success>false</success><errors>массив ошибок</errors>
