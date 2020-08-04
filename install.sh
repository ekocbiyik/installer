#!/bin/sh

###### Docker Kurulum #####
echo "docker servisi kuruluyor, lütfen root kullanıcı şifresini giriniz!"
sudo rpm -ivh --replacefiles --replacepkgs docker/*.rpm

echo "kurulum tamamlandı, servis başlatılıyor..."
sudo systemctl enable docker.service
sudo systemctl start docker.service

echo "$USER kullanıcısı için docker erişimi sağlanıyor..."
sudo groupadd docker
sudo gpasswd -a $USER docker

echo "servis yeniden başlatılıyor.."
sudo service docker restart


###### asistan kurulumu #####
DEST_PATH="$HOME/docker-asistan"
JRE="$DEST_PATH/jre/bin/java"


echo "
[Desktop Entry]
Version=1.0
Type=Application
erminal=false
Exec=$JRE -jar $DEST_PATH/docker-asistan.jar
Icon=$DEST_PATH/logo.png
Name=docker Asistan
" > docker-asistan.desktop


echo "dosyalar kopyalanıyor..."

rm -rf $DEST_PATH
echo "dizin silindi!"

mkdir $DEST_PATH
echo "yeni dizin oluşturuldu.."

cp -r $(pwd)/* $DEST_PATH/
echo "kopyalama işlemi tamamlandı!"

cp docker-asistan.desktop $HOME/.local/share/applications/
cp docker-asistan.desktop $HOME/Desktop/
gio set $HOME/.local/share/applications/docker-asistan.desktop "metadata::trusted" yes
gio set $HOME/Desktop/docker-asistan.desktop "metadata::trusted" yes
echo "masaüstü link oluşturuldu!"

echo "bilgisayar yeniden başlatılacak!"
sudo reboot
exit 1
