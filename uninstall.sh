#!/bin/sh

echo "docker servisi kaldırılıyor, lütfen root kullanıcı şifresini giriniz!"
sudo yum remove docker-ce docker-ce-cli containerd.io
sudo rm -rf /var/lib/docker

echo "desktop asistan uygulaması kaldırılıyor..."
sudo rm -rf $HOME/.local/share/applications/desktop-asistan.desktop
sudo rm -rf "$HOME/desktop-asistan"

echo "işlem tamamlandır, bilgisayar yeniden başlatılacak!"
sudo reboot
exit 1
