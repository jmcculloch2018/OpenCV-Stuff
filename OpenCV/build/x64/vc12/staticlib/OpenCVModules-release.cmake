#----------------------------------------------------------------
# Generated CMake target import file for configuration "Release".
#----------------------------------------------------------------

# Commands may need to know the format version.
set(CMAKE_IMPORT_FILE_VERSION 1)

# Import target "zlib" for configuration "Release"
set_property(TARGET zlib APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(zlib PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/zlib.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS zlib )
list(APPEND _IMPORT_CHECK_FILES_FOR_zlib "${_IMPORT_PREFIX}/x64/vc12/staticlib/zlib.lib" )

# Import target "libtiff" for configuration "Release"
set_property(TARGET libtiff APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(libtiff PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C;CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "zlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/libtiff.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS libtiff )
list(APPEND _IMPORT_CHECK_FILES_FOR_libtiff "${_IMPORT_PREFIX}/x64/vc12/staticlib/libtiff.lib" )

# Import target "libjpeg" for configuration "Release"
set_property(TARGET libjpeg APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(libjpeg PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/libjpeg.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS libjpeg )
list(APPEND _IMPORT_CHECK_FILES_FOR_libjpeg "${_IMPORT_PREFIX}/x64/vc12/staticlib/libjpeg.lib" )

# Import target "libwebp" for configuration "Release"
set_property(TARGET libwebp APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(libwebp PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/libwebp.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS libwebp )
list(APPEND _IMPORT_CHECK_FILES_FOR_libwebp "${_IMPORT_PREFIX}/x64/vc12/staticlib/libwebp.lib" )

# Import target "libjasper" for configuration "Release"
set_property(TARGET libjasper APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(libjasper PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/libjasper.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS libjasper )
list(APPEND _IMPORT_CHECK_FILES_FOR_libjasper "${_IMPORT_PREFIX}/x64/vc12/staticlib/libjasper.lib" )

# Import target "libpng" for configuration "Release"
set_property(TARGET libpng APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(libpng PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "C"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "zlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/libpng.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS libpng )
list(APPEND _IMPORT_CHECK_FILES_FOR_libpng "${_IMPORT_PREFIX}/x64/vc12/staticlib/libpng.lib" )

# Import target "IlmImf" for configuration "Release"
set_property(TARGET IlmImf APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(IlmImf PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "zlib"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/IlmImf.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS IlmImf )
list(APPEND _IMPORT_CHECK_FILES_FOR_IlmImf "${_IMPORT_PREFIX}/x64/vc12/staticlib/IlmImf.lib" )

# Import target "opencv_hal" for configuration "Release"
set_property(TARGET opencv_hal APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_hal PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_hal300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_hal )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_hal "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_hal300.lib" )

# Import target "opencv_core" for configuration "Release"
set_property(TARGET opencv_core APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_core PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;zlib;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_core300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_core )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_core "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_core300.lib" )

# Import target "opencv_flann" for configuration "Release"
set_property(TARGET opencv_flann APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_flann PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_flann300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_flann )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_flann "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_flann300.lib" )

# Import target "opencv_imgproc" for configuration "Release"
set_property(TARGET opencv_imgproc APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_imgproc PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_imgproc300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_imgproc )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_imgproc "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_imgproc300.lib" )

# Import target "opencv_ml" for configuration "Release"
set_property(TARGET opencv_ml APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_ml PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_ml300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_ml )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_ml "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_ml300.lib" )

# Import target "opencv_photo" for configuration "Release"
set_property(TARGET opencv_photo APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_photo PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_photo300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_photo )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_photo "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_photo300.lib" )

# Import target "opencv_video" for configuration "Release"
set_property(TARGET opencv_video APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_video PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_video300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_video )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_video "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_video300.lib" )

# Import target "opencv_imgcodecs" for configuration "Release"
set_property(TARGET opencv_imgcodecs APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_imgcodecs PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;ippicv;zlib;libjpeg;libwebp;libpng;libtiff;libjasper;IlmImf"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_imgcodecs300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_imgcodecs )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_imgcodecs "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_imgcodecs300.lib" )

# Import target "opencv_shape" for configuration "Release"
set_property(TARGET opencv_shape APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_shape PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_video;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_shape300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_shape )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_shape "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_shape300.lib" )

# Import target "opencv_videoio" for configuration "Release"
set_property(TARGET opencv_videoio APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_videoio PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_imgcodecs;ippicv;vfw32"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_videoio300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_videoio )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_videoio "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_videoio300.lib" )

# Import target "opencv_highgui" for configuration "Release"
set_property(TARGET opencv_highgui APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_highgui PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_imgcodecs;opencv_videoio;ippicv;comctl32;gdi32;ole32;setupapi;ws2_32"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_highgui300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_highgui )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_highgui "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_highgui300.lib" )

# Import target "opencv_objdetect" for configuration "Release"
set_property(TARGET opencv_objdetect APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_objdetect PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_ml;opencv_imgcodecs;opencv_videoio;opencv_highgui;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_objdetect300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_objdetect )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_objdetect "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_objdetect300.lib" )

# Import target "opencv_superres" for configuration "Release"
set_property(TARGET opencv_superres APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_superres PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_video;opencv_imgcodecs;opencv_videoio;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_superres300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_superres )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_superres "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_superres300.lib" )

# Import target "opencv_ts" for configuration "Release"
set_property(TARGET opencv_ts APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_ts PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_imgproc;opencv_imgcodecs;opencv_videoio;opencv_highgui;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_ts300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_ts )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_ts "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_ts300.lib" )

# Import target "opencv_features2d" for configuration "Release"
set_property(TARGET opencv_features2d APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_features2d PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_flann;opencv_imgproc;opencv_ml;opencv_imgcodecs;opencv_videoio;opencv_highgui;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_features2d300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_features2d )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_features2d "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_features2d300.lib" )

# Import target "opencv_calib3d" for configuration "Release"
set_property(TARGET opencv_calib3d APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_calib3d PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_flann;opencv_imgproc;opencv_ml;opencv_imgcodecs;opencv_videoio;opencv_highgui;opencv_features2d;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_calib3d300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_calib3d )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_calib3d "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_calib3d300.lib" )

# Import target "opencv_stitching" for configuration "Release"
set_property(TARGET opencv_stitching APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_stitching PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_flann;opencv_imgproc;opencv_ml;opencv_imgcodecs;opencv_videoio;opencv_highgui;opencv_objdetect;opencv_features2d;opencv_calib3d;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_stitching300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_stitching )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_stitching "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_stitching300.lib" )

# Import target "opencv_videostab" for configuration "Release"
set_property(TARGET opencv_videostab APPEND PROPERTY IMPORTED_CONFIGURATIONS RELEASE)
set_target_properties(opencv_videostab PROPERTIES
  IMPORTED_LINK_INTERFACE_LANGUAGES_RELEASE "CXX"
  IMPORTED_LINK_INTERFACE_LIBRARIES_RELEASE "opencv_hal;opencv_core;opencv_flann;opencv_imgproc;opencv_ml;opencv_photo;opencv_video;opencv_imgcodecs;opencv_videoio;opencv_highgui;opencv_features2d;opencv_calib3d;ippicv"
  IMPORTED_LOCATION_RELEASE "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_videostab300.lib"
  )

list(APPEND _IMPORT_CHECK_TARGETS opencv_videostab )
list(APPEND _IMPORT_CHECK_FILES_FOR_opencv_videostab "${_IMPORT_PREFIX}/x64/vc12/staticlib/opencv_videostab300.lib" )

# Commands beyond this point should not need to know the version.
set(CMAKE_IMPORT_FILE_VERSION)
