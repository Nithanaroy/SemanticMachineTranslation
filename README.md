# Semantic Machine Translation
Application that converts a sentence from English to German using the semantics of the sentence. This is unlike other statistical translators which blindly perform a word to word replacement with probability statistics models.

# How to run
- Import this repo as an existing project in eclipse
- In eclipse, right click on src/ folder and select "Use as Source Folder" under "Build Path" menu as shown [here] (https://goo.gl/photos/haXPUoxQrBZd9GYP8)
- Download the external libraries from [here](https://drive.google.com/folderview?id=0BxCCmPeFUyx4TXNjU1Y2aF9JOWM&usp=sharing "Vendor Folder to download")
- Add `kparser.jar` to your build path in eclipse
- Update the value `<param name="dictionary_path" value="__SOME_PATH__/vendor/resources/WordNet-3.0/dict"/>` in `wn_file_properties.xml` file inside `wsd_resources/` folder you downloaded from above link
- Run TestKParser.java file in the src/ folder
