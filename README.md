# Semantic Machine Translation
Application that converts a sentence from English to German using the semantics of the sentence. This is unlike other statistical translators which blindly perform a word to word replacement with probability statistics models.

# How to run
- Import this repo as an existing project in eclipse
- Download the external libraries from [here](https://drive.google.com/folderview?id=0BxCCmPeFUyx4TXNjU1Y2aF9JOWM&usp=sharing "Vendor Folder to download")
- Add `kparser.jar` and other jar files in vendor/ directory to your build path in eclipse
- Update the value `<param name="dictionary_path" value="__SOME_PATH__/vendor/resources/WordNet-3.0/dict"/>` in `wn_file_properties.xml` file inside `wsd_resources/` folder you downloaded from above link
- Install Python 2.7 if not already installed
- Install `nktl` python package as mentioned [here](http://www.nltk.org/install.html). Tip: `sudo easy_install pip` to install `pip` command on a MAC.
- Run TestKParser.java file in the src/ folder
