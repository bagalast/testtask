# Тестовое задание с логами

### Для запуска необходимо выполнить следующую команду:
```
java -jar [путь к .jar] [путь к логам]
```

В моем случае было так:
```
java -jar "E:\Advent Production\LogTransaction\build\libs\LogTransaction-1.0-SNAPSHOT.jar" "C:\Users\bagalast\Desktop\Проверка\logs"
```

## Примечание
При входных данных:
log1.log
```
[2025-05-10 09:00:22] user001 balance inquiry 1000.00
[2025-05-10 09:05:44] user001 transferred 100.00 to user002
[2025-05-10 09:06:00] user001 transferred 120.00 to user002
[2025-05-10 10:30:55] user005 transferred 10.00 to user003
[2025-05-10 11:09:01] user001 transferred 235.54 to user004
[2025-05-10 12:38:31] user003 transferred 150.00 to user002
[2025-05-11 10:00:31] user002 balance inquiry 210.00
```
log2.log
```
[2025-05-10 10:03:23] user002 transferred 990.00 to user001
[2025-05-10 10:15:56] user002 balance inquiry 110.00
[2025-05-10 10:25:43] user003 transferred 120.00 to user002
[2025-05-10 11:00:03] user001 balance inquiry 1770
[2025-05-10 11:01:12] user001 transferred 102.00 to user003
[2025-05-10 17:04:09] user001 transferred 235.54 to user004
[2025-05-10 23:45:32] user003 transferred 150.00 to user002
[2025-05-10 23:55:32] user002 withdrew 50
```
Для некоторых пользователей финальный баланс может быть отрицательным, т.к. начальный баланс не указывается, поэтому он загружает в память начальный баланс 0.0. Возможно стоило указать начальный баланс хотя-бы 1000.00.
