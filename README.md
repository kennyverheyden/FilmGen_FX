 <h1>FilmGen</h1>

<h3>The idea</h3>

The idea of this program is that it <strong>generates the title and description of a movie</strong> by randomly grabbing words from a database.

This idea is based on sakhila_master's database, in which the movie table shows all generated movies instead of actual movies.

Output of this program can <strong>stored in the database</strong> and <strong>write to a text file</strong>.

<h3>How will it work</h3>

A title and description is created by choosing random words from a database. That can be just a word, a name of a place, etcetera…

The title and description consists of a template and the database chooses words from other tables.

Title:

<code>word + word2</code>

Description:

<code>“(A/An) (hyperbolic) (story) of (a/an) (subject) and (a/an) (subject) who must (verb) (a/an) (subject) in (location)” </code>

The indefinite article is dynamic of the next word. 

<h3>Database</h3>

The database is used in <strong>SQLite</strong>.

All generated titles & descriptions are stored in a relational database with <strong>foreign keys</strong>.

User can edit database by adding and deleting values to the tables.

<strong>Referential integrity</strong>, restrict delete. User cannot delete a value in the database when a foreign key points to it.

![filmgen](https://user-images.githubusercontent.com/54863392/214706084-7b204267-81c5-4faf-abb3-e50587da8453.png)
