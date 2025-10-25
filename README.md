# Group06Project02

## Recommended Android Studio settings

To ensure that we share the same code formatting settings, ensure that Android Studio is configured like so:

- `Editor` > `Code Style`
  - Check `Enable EditorConfig support` (default)
- `Tools` > `Actions on Save`
  - Check `Reformat code`.
    - Under "All file types", ensure `Kotlin` is checked
  - Check `Optimize imports`.
    - Under "All file types", ensure `Kotlin` is checked
- `Editor` > `General` (scroll to "On Save" section)
  - Check `Remove trailing blank lines at the end of saved files`
  - Check `Ensure every saved file ends with a line break`

Now, Android Studio is configured to read code formatting settings from `.editorconfig`.

## Formatting with Spotless

```shell
./gradlew spotlessApply
```

## Optional

```shell
./gradlew spotlessInstallGitPrePushHook
```
