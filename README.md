# WikipediaAnalysis with OpenCompare

Examples for using OpenCompare WikipediaAnalysis API and services

It is possible to directly display and save the chart of several operations on a PCM dataset

The conformity of the fetaures or products of the whole dataset or only the matrices which are respected some characteristics
Occurrences of the features / products of the whole dataset or only some matrices.
The similarity of the the whole dataset or only some matrices.
Simillarity of the two pcm features .
Simillarity of the two pcm products.
Simillarity between two pcm.
Compare a PCM to the other PCM of the dataset.
        
#Import a PCM dataset
1- Define the files that you want to load.

2- Load the PCMs
Use loadAllPcmFromDirectory(pathOfYourFiles) to get directly a PCM list.

```java
        List<PCM> pcmlist = Tools.loadAllPcmFromDirectory("yourPath");
```

# Analyse the dataset

## To get the conform PCMs
The are many PCM in the dataset the API can't analyse because they are no features or products or they contains unreadble characters.
```java
pcmlist = Tools.conformsPCM(pcmlist);
```
## Features occurrences
```java
Map<String, Long> occurr = new HashMap<>();
occurr = Tools.featuresFrequencies(pcmlist);

//Save features occurencies as csv file
CsvTools.convertMapToCsv(occurr,"myFile.csv");

// To save a JPG image of the chart of occurr
Chart.getChart(occurr,20,"Features occurrencies","Top 20",1300,900,"./myFolder");

```
It is also possible to load a csv file to visualize and save it data as JPG diagram. With the last *boolean* parameter it is possible show or not the diagram in a popup window after saving it.
```java
Chart.getChart(loadCsvData("myFile.csv"),20,"Features occurrencies","Top 20",1300,900,"./myFolder",true);
```
