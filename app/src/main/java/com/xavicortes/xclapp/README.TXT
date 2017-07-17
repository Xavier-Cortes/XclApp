Opcions realitzades
General
    al drawer mostra la opció de desconectar a totes les opcions
    les opcions s'ha programat amb fragments.
    La Activity de les opcions es MAIN activity

Login
    Amb SQLITE
    Permet registrar i identificarse
    En cas d'error mostra activity ambn posibilitat de reintentar


Memory:
    4x4 amb 8 imatges sense posibilitat de modificar tamany
    Càlcula els intents essent el mímin de intents la millor puntuació
    Permet reiniciar des de el drawer
    Al finalitzar mostra un dialeg indicant que es el final i mostra els intents
    La milllor puntuació queda guardada a la fitza del usuari
    Es bloqueja la rotació de la pantalla.
    Es fa servir unAsyncTask per mantenir la visualització de les cartes voltejades (2seg)

Ranking
    Base de dades OK
    Es mostra en un recyclerview la llista de puntuacions ordenada de menor valor a major.
    Els usuari que no hagin fet cap partida, o sigui valor 0 a puntuació no es mostren


Mùsica
    Reproductor de una sola canço
    Tres pulsadors  Marcha, Parada, Pausa
    MediaPalyer executat utilizant el mètode del Boundservice

Perfil
    Mostra la fitxa a la pantalla del usuari actual
    Nom, contrasenya, metode avisos puntuació
    Pot escriure la direcció
    NO RECUPERA LA POSICIÓ DEL GPS
    Permet desconnectar per pulsador
    Guarda les modificacions realitzades a la fitxa del usuari
    NO AMAGA EL TECLAT quan marxa del fragment

Calculadora
    Teclat propi
    Mente el estat al girar
    NO HE FET DOS Layouts distints al girar.
    En cas d'expressió incorrecte es mostra el error en la casella de resultat
    Totes les opcions del ActionBar
    Guardar el tipus de notifiacions a la fitxa del usuari
    Te en compta la prioritat de operacions entre addicions i producte
    Permet la memori ans











