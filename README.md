
* https://start.spring.io/ üzerinden jdk11 olacak şekilde bir proje oluşturulur

* application.property dosyasına "spring.main.web-application-type=none" eklenir

* fx-controls ve fxml kütüphaneleri pom.xml'e eklenir  

* https://gluonhq.com/products/javafx/ javaFX sdk indirilir (java8 sonrası jdk'dan ayrıldığı için)

* main class düzenlenir, fxml componentleri eklenir

* intellij üzerinde geliştirme yapılıyorsa aşağıdaki bilgiler edit configurations-> vm options kısmına yazılmalı 
    --module-path /opt/javafx-sdk11/lib/ --add-modules javafx.controls,javafx.fxml

* jdk14+ kullanılıyorsa aşağıdaki gibi eklenmeli
    --module-path /opt/javafx-sdk11/lib/ --add-modules javafx.controls,javafx.fxml  --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED

* isteğe bağlı fx-maven plugini projeye eklenebilir, executable jar oluşturmanızı sağlar. Fakat spring boot bunu zaten sağlıyor.


* bu uygulama production ortamındaki docker containerlarını yönetmeye ve yeni imaj upload etmeye yarıyor.

* istenilen projeye ait docker imajı build edilir
     docker build -f  Dockerfile -t my-iamge:2.0.1r2 --no-cache .

* oluşturulan imaj .tar uzantılı olacak şekilde dizine kaydedilir
     docker save -o my-iamge.tar my-iamge:2.0.1r2

* tar dosyası production ortamına atılır, bu uygulamadaki versiyon ekranından .tar dosyası seçilir ve yükle butonuna tıklanır (bkz. DockerUtils.loadImage() metodu)

* dosyada hata yoksa imaj sisteme başarılı olarak yüklenir, aynı ekranda bulunan tabloda seçili olarak işaretlenir

* tablodaki upgrade butonuna tıklanırsa ilgili servis, istenilen versiyona güncellenmiş olur (bkz. DockerUtils.runContainer() metodu)



--module-path /opt/javafx-sdk14/lib/ --add-modules javafx.controls,javafx.fxml  --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED

