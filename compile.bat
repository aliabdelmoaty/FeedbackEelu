@echo off
echo Compiling Student Feedback System...

:: Create bin directory if it doesn't exist
if not exist bin mkdir bin

:: Compile all Java files
javac -d bin ^
    Main.java ^
    db/Database.java ^
    register/Register.java ^
    loginScreen/Login.java ^
    feedback/FeedBack.java

if %errorlevel% equ 0 (
    echo Compilation successful!
    echo You can run the program using: java -cp bin Main
) else (
    echo Compilation failed!
)

pause 