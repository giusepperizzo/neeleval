neeleval
========

### [#Microposts2014 NEEL Challenge](http://www.scc.lancs.ac.uk/microposts2014/challenge/index.html) Evaluation Framework

##### Prerequisites
- Java v1.6+
- Maven v3+

##### Evaluate: compute F1, R, P figures

java -cp neeleval.jar neeleval.Evaluate GS TS

where:
* GS (Gold Standard) tab separated file. Each line should contain i) tweetid, entityMention1, entityURI1, ... , entityMentionN, entityURIN
* TS (Test Set) tab separated file. Each line should contain i) tweetid, entityMention1, entityURI1, ... , entityMentionN, entityURIN

outputs:
* precision, recall, and F1

##### EvaluateRank @x : compute F1, R, P figures
java -cp neeleval.jar neeleval.EvaluateRank GS TS x

where:
* GS (Gold Standard) tab separated file. Each line should contain i) tweetid, entityMention1, entityURI1, ... , entityMentionN, entityURIN
* TS (Test Set) tab separated file. Each line should contain i) tweetid, entityMention1, entityURI1, ... , entityMentionN, entityURIN
* x (cut-off of the rank) integer from 1 ... n

outputs:
* precision@x, recall@x, and F1@x

##### Redirect: resolve redirects

java -cp neeleval.jar neeleval.ResolveRedirect TS /path/where/to/save/TS/with/redirects

where:
* TS (Test Set) tab separated file. Each line should contain i) tweetid, entityMention1, entityURI1, ... , entityMentionN, entityURIN
* TS (Test Set) which contains all redirects (where needed) 
