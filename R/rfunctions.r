

#Affiche le diagramme des 20 plus grandes occurrences des données csv des products chargés
plotP <- function(path) {
	data = read.csv(path)
pnames=data[,1]
pnum=data[,2]

infNum = 0
if(length(pnum)>20){
	infNum = (length(pnum) - 20)
}

infName = 0
if(length(pnum)>20){
	infName = (length(pnames) - 20)
}

y = pnum[infNum:length(pnum)]
x = pnames[infName:length(pnames)]
names(y)=x
label = paste("All data ",length(pnum)," | SD: ",sd(pnum)," |  Avg: ",mean(pnum))
barplot(y, col=rgb(0.0,0.0,1,1), xlab=label, ylab="occurrences", main="Products occurrences" , ylim=c(0,floor(max(y)/10) *10 + 10) )
}

#Affiche le diagramme des 20 plus grandes occurrences des données csv des features chargés
plotF <- function(path) {
data = read.csv(path)
pnames=data[,1]
pnum=data[,2]
infNum = 0
if(length(pnum)>20){
	infNum = (length(pnum) - 20)
}

infName = 0
if(length(pnum)>20){
	infName = (length(pnames) - 20)
}
y = pnum[infNum:length(pnum)]
x = pnames[infName:length(pnames)]
names(y)=x
label =paste("All data ",length(pnum)," | SD: ",sd(pnum)," |  Avg: ",mean(pnum)); 
barplot(y, col=rgb(0.0,0.0,1,1), xlab=label, ylab="occurrences", main="Features occurrences" , ylim=c(0,floor(max(y)/10) *10 + 10) )
}


loadPcmStat <- function(path) {
	csvData = d=read.csv(file=path,sep=',',header=F)
	n<-csvData[,1]
	nbl<-csvData[,2]
	nbc<-csvData[,3]
	tm<-csvData[,4]
	pcms<- n[3:length(n)]
	nbLigne <- nbl[3:length(nbl)]
	nbCol <- nbc[3:length(nbc)]
	tailles <- tm[3:length(tm)]

	matr <- matrix(c(nbLigne,nbCol,tailles),nrow=length(nbLigne),3)
csvData
}
