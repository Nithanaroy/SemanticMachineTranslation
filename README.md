# Semantic Machine Translation
Application that converts a sentence from English to German using the semantics of the sentence. This is unlike other statistical translators which blindly perform a word to word replacement with probability statistics models.

# Setting up Dev Env
- Import this repo as an existing project in eclipse
- Install Python 2.7 if not already installed
- Install Jython from [here](http://www.jython.org/downloads.html) using the **installer** and NOT the standalore JAR. Remember the directory where it is installing. We need it later. Pick Standard Installation during the installation process
- Install `nktl` python package as mentioned [here](http://www.nltk.org/install.html). Tip: `sudo easy_install pip` to install `pip` command on a MAC.
- By default `nltk` will be installed in `Lib/` folder of your python installation directory, usually `/Users/<username>/Python2.7`. Copy `nltk/` and `nltk-***egg.info/` folders from that directory to `Lib/` folder of Jython's installation directory
- Update `Jython*.jar`'s path in your project's build path in eclipse


## External Libraries
- Download the external libraries from [here](https://drive.google.com/folderview?id=0BxCCmPeFUyx4TXNjU1Y2aF9JOWM&usp=sharing "Vendor Folder to download")
- Update `kparser.jar` and other jar files in `vendor/` directory paths in your build path in eclipse
- Update the value `<param name="dictionary_path" value="__SOME_PATH__/vendor/resources/WordNet-3.0/dict"/>` in `wn_file_properties.xml` file inside `wsd_resources/` folder you downloaded from above link

- Run TestKParser.java file in the src/ folder

**Note:** If you use WordNet Stemmer in nltk, you have to download WordNet corpus using `nltk.download()` in python terminal (REPL) and install to default location pointed by the downloader. Other stemmers work just fine.