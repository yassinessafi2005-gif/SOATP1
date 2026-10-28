# Compte rendu – TP1 : Modélisation XML et interrogation XPath

## 1. Présentation du travail

Ce travail porte sur la modélisation d’un cinéma à l’aide de XML, la définition d’un schéma XSD pour contrôler la structure des données, puis la validation et l’interrogation du document depuis un programme Java utilisant Maven.

Le domaine choisi est la gestion de films et de leurs projections. Le modèle comprend plusieurs objets liés entre eux : le cinéma, les réalisateurs, les films, les acteurs et les projections.

## 2. Objectifs

- Représenter des données de cinéma dans un document XML.
- Définir des types simples et des types complexes dans un schéma XSD.
- Vérifier que le document XML respecte ce schéma.
- Utiliser XPath depuis Java pour extraire des informations du document.

## 3. Modélisation des données

La racine du document est `cinema`. Elle contient le nom du cinéma, une liste de réalisateurs et une liste de films.

Chaque réalisateur possède un identifiant, un nom et une nationalité. Chaque film possède un identifiant, un titre, une année, un genre, une note et une référence vers son réalisateur. Un film contient aussi une liste d’acteurs et une liste de projections. Chaque acteur est décrit par son identifiant, son nom, son rôle et l’indication qu’il soit ou non l’acteur principal. Chaque projection comporte une salle, une date, une heure et un prix.

Les identifiants permettent de distinguer les objets. La valeur de `realisateurRef` fait référence à l’identifiant d’un réalisateur déclaré dans le document.

## 4. Fichiers du projet

```text
projet/
├── pom.xml
├── films.xml
├── films.xsd
└── src/
    └── main/
        └── java/
            └── org/example/Main.java
```

- `films.xml` contient les données du cinéma.
- `films.xsd` décrit la structure attendue et les règles de validation.
- `Main.java` valide le XML puis effectue les requêtes XPath.
- `pom.xml` configure le projet Maven.

## 5. Types définis dans le XSD

### Types simples

Le schéma définit des contraintes sur certaines valeurs :

- `GenreFilm` limite le genre à une liste de valeurs autorisées : Action, Drame, Comedie, ScienceFiction ou Animation.
- `NoteFilm` accepte une valeur décimale comprise entre 0 et 10, avec au plus un chiffre après la virgule.
- `AnneeFilm` représente une année et limite sa valeur à l’intervalle 1888–2100.
- D’autres types intégrés à XML Schema sont utilisés, tels que `xs:string`, `xs:boolean`, `xs:date`, `xs:time`, `xs:decimal`, `xs:ID` et `xs:IDREF`.

### Types complexes

Le schéma déclare plusieurs types complexes :

- `RealisateurType` décrit un réalisateur.
- `ActeurType` décrit un acteur et son rôle.
- `ProjectionType` décrit une projection.
- `FilmType` regroupe les informations du film ainsi que ses acteurs et ses projections.

Les listes de films, acteurs et projections acceptent plusieurs éléments grâce à `maxOccurs="unbounded"`.

## 6. Validation XML/XSD

Le programme Java charge `films.xsd`, crée un validateur avec `SchemaFactory` et vérifie `films.xml` avec `Validator`. Si le document respecte le schéma, le programme affiche un message de validation. Si une valeur ou une structure ne respecte pas le XSD, une exception indique l’erreur rencontrée.

Cette validation permet notamment de détecter un genre non autorisé, une note hors de l’intervalle prévu, un élément manquant ou un ordre d’éléments incorrect.

## 7. Requêtes XPath réalisées

Le programme utilise XPath pour :

1. Lire tous les films et afficher leur titre, leur genre et leur note.
2. Trouver les acteurs principaux avec le prédicat `[@principal='true']`.
3. Lire toutes les projections et afficher leur date, leur heure, leur salle et leur prix.

Exemples d’expressions utilisées :

```xpath
/cinema/films/film
/cinema/films/film/acteurs/acteur[@principal='true']
/cinema/films/film/projections/projection
```

## 8. Exécution

1. Ouvrir le projet Maven dans IntelliJ IDEA.
2. Vérifier que `films.xml` et `films.xsd` sont à la racine du projet, au même niveau que `pom.xml`.
3. Vérifier que `Main.java` se trouve dans `src/main/java/org/example/` et que sa déclaration de package est `package org.example;`.
4. Lancer la classe `Main` avec le bouton **Run**.

Les API Java utilisées pour XML, XSD et XPath (`javax.xml`) sont fournies par le JDK. Aucune dépendance Maven supplémentaire n’est nécessaire pour ces fonctionnalités.

## 9. Résultat attendu

Si les fichiers sont corrects, la console affiche d’abord que le XML est valide selon le XSD, puis les films, les acteurs principaux et les projections trouvées par XPath. Les résultats affichés dépendent des données présentes dans `films.xml`.

## 10. Conclusion

Ce TP montre comment structurer des données hiérarchiques avec XML, contrôler leur validité avec XSD et extraire des informations avec XPath en Java. La séparation entre le document de données, le schéma et le programme facilite la vérification et l’évolution du modèle.
