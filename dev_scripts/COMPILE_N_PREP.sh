#!/bin/bash
clear
echo "Andreo Development Script - Tarik Al-Ani 2010 (C)"
DATE=`date +%m%d`
CUR_VER=0.2.21
LATEST_BUILD=$CUR_VER.${DATE#0}

cd '/home/tarik/Desktop/latestbuilds'
echo "LATEST BUILD = $LATEST_BUILD"
mkdir -p $LATEST_BUILD
cd $LATEST_BUILD
echo ""
filename=android.ubi

echo "- Check for existing build"
if [ -f "$filename" ]; then 
	echo "- Found existing build - Moving to backup"
	mkdir -p backup
	mv -u android.ubi 'backup/android.ubi'
	echo "- Backup Done"
fi

echo "- TOUCH - Camera Services"
touch '/home/tarik/workingfolder2/hardware/omap3/camera/CameraHardware.cpp'
touch '/home/tarik/workingfolder2/hardware/omap3/camera/converter.cpp'
touch '/home/tarik/workingfolder2/hardware/omap3/camera/V4L2Camera.cpp'
touch '/home/tarik/workingfolder2/hardware/omap3/camera/converter.h'
touch '/home/tarik/workingfolder2/frameworks/base/camera/libcameraservice/CameraService.cpp'

echo ""
echo "---- COMPILING CODE ----"

cd '/home/tarik/workingfolder2/'

echo
echo -n "Do you want to proceed with compiling (Y/N)?"
read answer

if test "$answer" != "Y" -a "$answer" != "y";
then echo "" echo "Skipping 'make'";
else 
	echo ""	
	echo "- compiling'"
	make -j8;
fi

echo ""
echo "- Copy system.img to latest build"
cp '/home/tarik/workingfolder2/out/target/product/devkit8000/system.img' '/home/tarik/Desktop/latestbuilds/'$LATEST_BUILD 
cd '/home/tarik/Desktop/latestbuilds/'$LATEST_BUILD
echo "- Rename to 'android.ubi'"
mv system.img android.ubi
echo ""
echo "---- DONE ----"
