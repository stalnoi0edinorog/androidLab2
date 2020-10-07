# Лабораторная работа №2. Activity Lifecycle. Alternative resources.  
## Цели  
* Ознакомиться с жизненным циклом Activity  
* Изучить основные возможности и свойства alternative resources  
  
## Задачи  
### Задача 1. Activity  
#### Задание  
Продемонстрировать жизненный цикл Activity на любом нетривиальном примере.  

#### Пример №1. Переход в режим энергосбережения
При запуске вызываются следующие методы onCreate() -> onStart,() -> onResume(). При переходе в режим энергосбережения была вызвана следующая цепочка методов:
onPause() -> onStop() -> onSaveInstanceState() -> onDestroy() -> onCreate() -> onStart() -> onRestoreInstanceState() -> onResume().

#### Пример №2. Звонок на телефон
При ответе на вызов были вызваны следующие методы onPause() -> onStop() -> onSaveInstanceState(). После возвращения в приложение (независимо от того был сброшен звонок или нет) была вызвана следующая цепочка методов: onRestart() -> onStart() -> onResume().

### Задача 2. Alternative Resources  
#### Задание  
Продемонстрировать работу альтернативного ресурса ** UI mode ** на каком-либо примере.
Была создана отдельная директория для интерфейса под телевизор. Идея проста - размер шрифта, удобного для просмотра с телефона, будет слишком мелок для прочтения с телевизора.

### Задача 3. Best-matching resource  
Для заданного набора альтернативных ресурсов, предоставляемых приложением, и заданной конфигурации устройства объяснить, какой ресурс будет выбран в конечном итоге. Ответ доказать.
#### Вариант 8. 
Конфигурация устройства
- LOCALE_LANG: fr
- LOCALE_REGION: rFR
- SCREEN_SIZE: normal
- SCREEN_ASPECT: notlong
- ROUND_SCREEN: notround
- ORIENTATION: land
- UI_MODE: television
- NIGHT_MODE: notnight
- PIXEL_DENSITY: tvdpi
- TOUCH: notouch
- PRIMARY_INPUT: qwerty
- NAV_KEYS: nonav
- PLATFORM_VER: v26

Конфигурация ресурсов:
- (default)
- round-port-watch-ldpi-v25
- rFR-watch-dpad
- long-notround-night
- rFR-notround-notnight-xhdpi-v25
- rCA-xlarge-notround-television-night-nokeys-wheel-v25
- en-round-vrheadset-nokeys
- en-long-port-nodpi-trackball
- notround-nokeys
- en-normal-notouch
- en-rCA-notlong

**Удаляем язык en, т.к. в конфигурации стоит fr**
- (default)
- round-port-watch-ldpi-v25
- rFR-watch-dpad
- long-notround-night
- rFR-notround-notnight-xhdpi-v25
- rCA-xlarge-notround-television-night-nokeys-wheel-v25
- ~~en-round-vrheadset-nokeys~~
- ~~en-long-port-nodpi-trackball~~
- notround-nokeys
- ~~en-normal-notouch~~
- ~~en-rCA-notlong~~

**Исключаем строчки, в которых указан только регион**
- (default)
- round-port-watch-ldpi-v25
- ~~rFR-watch-dpad~~
- long-notround-night
- ~~rFR-notround-notnight-xhdpi-v25~~
- ~~rCA-xlarge-notround-television-night-nokeys-wheel-v25~~
- notround-nokeys

**Исключаем круглый экран, т.к. в конфиг-ии прямоугольный**
- (default)
- ~~round-port-watch-ldpi-v25~~
- notround-nokeys

**Исключаем прямоугольный экран, т.к. устройство должно иметь qwerty-клавиатуру**
- (default)
- ~~notround-nokeys~~

**Итог: default**


### Задача 4. Сохранение состояние Activity.  
Студент написал приложение: [continuewatch](continuewatch). Это приложение [по заданию](continuewatch/README.md) должно считать, сколько секунд пользователь провел в этом приложении. Необходимо найти ошибки в этом приложении и исправить их.
Найденные ошибки:
- при запуске приложения отображается текст "Hello world". Следовательно, убираем атрибут "text" у TextView;
- счётчик продолжает увеличиваться, даже если приложение не активно. Значит добавляем локальную переменную check, которая будет "отвечать" за стостояние activity;
- значение счётчика не сохраняется при перезапуске. Добавим два метода onSaveInstanceState() и onRestoreInstanceState(). В первом будем "сохранять" прошедшее время, а во втором устанавливать его в счётчик.

