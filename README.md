
* https://start.spring.io/ üzerinden jdk14 olacak şekilde bir proje oluşturulur
* application.property dosyasına "spring.main.web-application-type=none" eklenir
* fx-controls ve fxml kütüphaneleri pom.xml'e eklenir  
* https://gluonhq.com/products/javafx/ javaFX sdk indirilir (java8 sonrası jdk'dan ayrıldığı için)
* main class düzenlenir, fxml componentleri eklenir
* intellij üzerinde geliştirme yapılıyorsa aşağıdaki bilgiler edit configurations-> vm options kısmına yazılmalı 
    --module-path /opt/javafx-sdk11/lib/ --add-modules javafx.controls,javafx.fxml
* jdk14+ kullanılıyorsa aşağıdaki gibi eklenmeli
    --module-path /opt/javafx-sdk11/lib/ --add-modules javafx.controls,javafx.fxml  --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED

* isteğe bağlı fx-maven plugini projeye eklenebilir, executable jar oluşturmanızı sağlar. Fakat spring boot bunu zaten sağlıyor.

--module-path /opt/javafx-sdk11/lib/ --add-modules javafx.controls,javafx.fxml  --add-exports javafx.graphics/com.sun.javafx.sg.prism=ALL-UNNAMED