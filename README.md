# Design patterns folosite
Singleton - ProiectPOO
- instanță unică în cadrul programului

Factory 
- pentru construirea Stream-urilor
- se pot extinde clasele specifice: Song, Podcast și Audiobook

Observer
- pentru stream-uri: relație one-to-many între user sau streamer și stream-uri
- Streamer este subiect, observatorii sunt de tip Stream (prima folosire)
- User este subiect, observatorii sunt de tip Stream (a doua folosire)
- starea este dată de numărul de stream-uri (în cadrul clasei Streamer)
- avem dependență între toate aceste clase pe parcursul întregii rezolvări a temei

Command - ComandaList
- cele două funcții cereri, implementate sub forma unor clase sunt ListStreamer și ListUser
- avem 2 posibilități de listare: pentru stream-urile unui streamer și pentru ale unui utilizator
- inițial nu știm pe care trebuie să o realizăm, dar se solicită listarea
- putem folosi, în aceste cazuri, doar o instanță a clasei Streamer sau avem nevoie de câte una din fiecare (Streamer + User)
