# hardware/libfreerunner_gps/Android.mk

ifeq ($(strip $(BOARD_USES_MBM_GPS)),true)

	LOCAL_PATH:= $(call my-dir)
	include $(CLEAR_VARS)

	LOCAL_SRC_FILES := \
		andreo-omap_gps.c

	LOCAL_MODULE := libandreo-gps

	LOCAL_SHARED_LIBRARIES := \
		libutils \
		libcutils \
		libdl \
		libc

	include $(BUILD_SHARED_LIBRARY)

endif
