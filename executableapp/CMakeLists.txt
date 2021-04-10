# For more information about using CMake with Android Studio, read the
# documentation: https://d.android.com/studio/projects/add-native-code.html

# Sets the minimum version of CMake required to build the native library.
cmake_minimum_required(VERSION 3.10)

# Creates and names a library, sets it as either STATIC
# or SHARED, and provides the relative paths to its source code.
# You can define multiple libraries, and CMake builds them for you.
# Gradle automatically packages shared libraries with your APK.

# 一次性添加多个文件到lib中，否则add_library中第三个参数要手动添加文件
#file(GLOB SOURCES "src/main/cpp/*.*")

#aux_source_directory(src/main/cpp cpp)
add_executable( # Sets the name of the library.
        hello
        # Provides a relative path to your source file(s).
        src/main/cpp/main.cpp)