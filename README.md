# Kotlin Script IDE

This is a kotlin desktop application made using compose multiplatform. The app consists text editor where 
you can type script and after that execute them. 

## Features

- Coding text area with syntax highlight
- File list that contains ```.kts``` files in selected directory
- Option to create new Kotlin script files
- Input/output terminal that displays script results or get user input
- Error information with navigation to the exact location of the error in the text editor

## Requirements

- Java JDK 21
- Kotlin command-line compiler(kotlinc)

## Instalation and setup

If you haven't already downloaded it, you need to install kotlinc command-line compiler. Follow this [installation guide](https://kotlinlang.org/docs/command-line.html). After installation, add the it's /bin folder to your system's global PATH variable.

### Windows setup

Navigate to the project's root directory and run:
```
gradlew.bat build
```
to build the application, and
```
gradlew.bat run
```
to execute it.
### Linux setup

Navigate to the project's root directory, and first, enable executable permissions for the ```gradlew``` script:
```
chmod +x gradlew
```

Then use:
```
gradlew build
```
to build application, or:
```
gradlew run
```
to run it
> [!WARNING]
> You need to have jdk-21 in ```PATH``` variable. If you're unsure of your Java version, check it with:
> ```
> java -version
> ```

## Usage

When you launch application, you will see GUI.
1. Go to **File->Open folder** and select directory where you want to store your scripts.
2. Click the **Add** button to create new file.
3. Double-click the newly created file in file list to open it.
4. Start typing your script in the editor.
5. Once you're done, click the **Run** button to execute script. The output will be displayed in the output pane






