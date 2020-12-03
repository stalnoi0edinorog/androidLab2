# Лабораторная работа №2. Activity Lifecycle. Alternative resources.  
## Цели  
* Ознакомиться с жизненным циклом Activity  
* Изучить основные возможности и свойства alternative resources  
  
## Задачи  
### Задача 1. Activity  
#### Задание  
Продемонстрировать жизненный цикл Activity на любом нетривиальном примере.  

#### Пример №1. Переход в режим энергосбережения
При запуске вызываются следующие методы onCreate() -> onStart,() -> onResume(). 
![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/start.JPG)

При переходе в режим энергосбережения была вызвана следующая цепочка методов:
onPause() -> onStop() -> onSaveInstanceState() -> onDestroy() -> onCreate() -> onStart() -> onRestoreInstanceState() -> onResume().
![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/energy.JPG)

#### Пример №2. Звонок на телефон
При ответе на вызов были вызваны следующие методы onPause() -> onStop() -> onSaveInstanceState(). 
![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/call.JPG)

После возвращения в приложение (независимо от того был сброшен звонок или нет) была вызвана следующая цепочка методов: onRestart() -> onStart() -> onResume().
![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/call2.JPG)

### Задача 2. Alternative Resources  
#### Задание  
Продемонстрировать работу альтернативного ресурса ** UI mode ** на каком-либо примере.
Была создана отдельная директория для интерфейса под телевизор. Идея проста - размер шрифта, удобного для просмотра с телефона, будет слишком мелок для прочтения с телевизора.

![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/phone.JPG)
![Иллюстрация к проекту](https://github.com/stalnoi0edinorog/androidLab2/blob/master/forReports/television.JPG)


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

## Выводы
Был изучен жизненный цикл Activity, основные возможности и свойства Alternative resources, алгоритм для выбора best-matching resource. Также, была проведена работа по нахождению ошибок, связанных с жизненным циклом activity, и их исправление в заданном приложении.
Также более подробно рассмотрела тему потоков в Android. Когда запускается компонент приложения, Android создает новый процесс с одним потоком исполнения. По умолчанию все компоненты одного приложения запускаются в одном процессе - в главном потоке (UI-поток). Если в приложении есть какие-либо визуальные элементы, то в этом потоке запускается объект класса Activity, отвечающий за отрисовку view. В главном Activity должно быть как можно меньше вычислений, единственная его задача — отображать UI, так как на момент их выполнения работа приложения "зависнет" до тех пор, пока вычисления не будут закончены. Если ожидание продлится чуть больше пары секунд, ОС Android это заметит и пользователь увидит сообщение об ошибке с предложением принудительно завершить работу. Для того, чтобы избежать такой ситуации необходимо добавить фоновый поток, в котором будут происходить все вычисления, но менять представление может только главный поток, поэтому необходимо каким-либо образом передать в него новые данные. Это можно сделать с помощью метода View.post(Runnable) или метода Activity.runOnUiThread(Runnable).

## Листинги

### MainActivity.kt

    package com.example.lab2

    import androidx.appcompat.app.AppCompatActivity
    import android.os.Bundle
    import android.os.PersistableBundle
    import android.util.Log
    import kotlinx.android.synthetic.main.activity_main.*

    class MainActivity : AppCompatActivity() {
        private val TAG = "Life_Cycle"
        private var check = true
        var secondsElapsed: Int = 0

    var backgroundThread = Thread {
        while (true) {
            Thread.sleep(1000)
            if (check)
                textSecondsElapsed.post {
                    textSecondsElapsed.setText("Seconds elapsed: " + secondsElapsed++)
                }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        backgroundThread.start()
        Log.d(TAG, "Activity created")
    }

    override fun onRestart() {
        super.onRestart()
        Log.d(TAG, "Activity restarted")
    }

    override fun onStart() {
        super.onStart()
        Log.d(TAG, "Activity became visible")
    }

    override fun onRestoreInstanceState(savedInstanceState: Bundle) {
        secondsElapsed = savedInstanceState.getInt("seconds")
        super.onRestoreInstanceState(savedInstanceState)
        Log.d(TAG, "Data restored")
    }

    override fun onResume() {
        super.onResume()
        check = true
        Log.d(TAG, "Activity resumed")
    }

    override fun onPause() {
        super.onPause()
        check = false
        Log.d(TAG, "Activity paused")
    }

    override fun onStop() {
        super.onStop()
        Log.d(TAG, "Activity stopped")
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt("seconds", secondsElapsed)
        super.onSaveInstanceState(outState)
        Log.d(TAG, "Data saved")
    }

    override fun onDestroy() {
        super.onDestroy()
        Log.d(TAG, "Activity destroyed")
    }

}
 ### Листинг values/styles.xml
 <resources>

    <!-- Base application theme. -->
    <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">
        <!-- Customize your theme here. -->
        <item name="colorPrimary">@color/colorPrimary</item>
        <item name="colorPrimaryDark">@color/colorPrimaryDark</item>
        <item name="colorAccent">@color/colorAccent</item>
        <item name="android:gravity">center</item>
        <item name="android:textColor">#000000</item>
        <item name="android:textSize">14sp</item>
    </style>

</resources>

### Листинг values-television/styles.xml
    <?xml version="1.0" encoding="utf-8"?>
    <resources>
        <style name="AppTheme" parent="Theme.AppCompat.Light.DarkActionBar">

            <item name="android:gravity">center</item>
            <item name="android:textColor">#000000</item>
            <item name="android:textSize">36sp</item>
        </style>

    </resources>
