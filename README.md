Proje Hakkında

Bu proje, Java tabanlı olarak geliştirilmiş bir E-Ticaret Web Uygulamasıdır. Kullanıcıların ürünleri görüntüleyebildiği, sepete ekleyebildiği ve sipariş oluşturabildiği; yöneticilerin ise ürün, kategori ve sipariş yönetimi yapabildiği bir sistemdir.

Proje MVC (Model-View-Controller) mimarisi kullanılarak geliştirilmiştir.

 Kullanılan Teknolojiler
Java (Servlet & JSP)
MySQL
JDBC
HTML / JSP
Apache Tomcat Server
MVC Mimari Yapısı
 Proje Yapısı
E-Ticaret-Projesi/
│
├── src/main/java
│   ├── controller
│   ├── dao
│   ├── model
│   └── util
│
├── src/main/webapp
│   ├── admin
│   ├── index.jsp
│   ├── login.jsp
│   ├── register.jsp
│   ├── cart.jsp
│   └── ...
│
└── WEB-INF
    ├── web.xml
    └── lib
 MVC Mimarisi
Model: User, Product, CartItem gibi veri sınıfları
View: JSP sayfaları (frontend)
Controller: Kullanıcı isteklerini yöneten servlet sınıfları
DAO: Veritabanı işlemlerini yöneten katman
Util: Veritabanı bağlantı sınıfı (DBConnection)
 Veritabanı

Proje MySQL üzerinde çalışmaktadır.

Tablolar:
users
categories
products
orders
order_items
İlişkiler:
Kullanıcı → Sipariş (1-N)
Sipariş → Sipariş Ürünleri (1-N)
Ürün → Sipariş Ürünleri (1-N)
Kategori → Ürün (1-N)
Çalıştırma Adımları
Projeyi Eclipse IDE’ye import edin
MySQL üzerinde eticaret_db veritabanını oluşturun
SQL scriptlerini çalıştırın
DBConnection sınıfında veritabanı bilgilerini kontrol edin
Apache Tomcat server üzerinde projeyi çalıştırın

Bilinen Sorunlar

Proje geliştirme sürecinde tüm kod yapısı ve veritabanı tasarımı tamamlanmıştır.
Ancak bazı teknik sebeplerden dolayı (veritabanı bağlantısı / server yapılandırması) proje çalışma aşamasında hata verebilmektedir.

Buna rağmen proje:

MVC mimarisiyle tamamlanmıştır
Veritabanı tasarımı oluşturulmuştur
Backend ve frontend yapısı geliştirilmiştir
