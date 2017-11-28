# Casper

Most companies that use Android phones have proprietary or classified information
stored somewhere on the phone. Casper is a Remote Administration Tool (RAT) that allows
admins to manipulate phones remotely to ensure data protection.

### Supported Phones
* Samsung Galaxy Note 4 (SM-N910H)
* Samsung Galaxy S3 (SPH-L710)
* Goldfish Emulator

### Samsung Setup
Download the correct OTA for your phone from https://firmware.mobi/  
Boot your phone into download mode (Volume Down + Home Button + Power Button)  
Flash the OTA via Odin (included in the OTA zip)  
Open the SuperSU app  
Open the Settings tab  
Set Default access to Grant  
Uncheck Show notifications  
Uncheck Re-authentication  
  
### Emulator Setup
setenforce 0 // to set selinux to permissive  
download busybox  
push to /data/local/tmp  
mkdir /data/busybox  
cp /data/local/tmp/busybox-x86_64  
mount | grep "system" // to find where system partition is located  
mount -o rw,remount -t ext4 /dev/block/vda /system  
  
// after finding the system location  
dd if=/dev/block/vda of=/data/local/tmp/system  
  
Go to C:\Users\Tyler\AppData\Local\Android\sdk\system-images\android-23\google_apis\x86_64  
make a backup of system.img  
copy system.img  
mount system.img  
place busybox in /bin  
  
Go to C:\Users\Tyler\AppData\Local\Android\sdk\system-images\android-23\google_apis\x86_64
make a backup of ramdisk.img  
gunzip ramdisk (might need to rename to ramdisk.gz and use --force)  
cpio -idv < ramdisk  
change /system to rw in fstab.ranchu  
change ro.secure from 1 to 0 in default.prop  
find . | cpio -o -H newc > ../ramdisk  
gzip it  
rename to ramdisk.img  
  
Start the emulator from command line C:\Users\Tyler\AppData\Local\Android\sdk\tools
emulator.exe -list-avds // find avd device  
emulator.exe -avd Nexus_5_API_23 // start the emulator  
  
IMPORTANT:  
During the mount_all command, the init executable loads all of the files
contained within the /{system,vendor,odm}/etc/init/ directories. These
directories are intended for all Actions and Services used after file system
mounting.  
