INSERT INTO genre (genre_id, name)
SELECT * FROM (VALUES
        (1, 'Комедия'),
        (2, 'Драма'),
        (3, 'Мультфильм'),
        (4, 'Триллер'),
        (5, 'Документальный'),
        (6, 'Боевик')
        ) AS new_genre(genre_id, name)
WHERE NOT EXISTS (
    SELECT 1 FROM genre
    WHERE genre.genre_id = new_genre.genre_id
);

INSERT INTO mpa (id, name)
SELECT * FROM (VALUES
        (1, 'G'),
        (2, 'PG'),
        (3, 'PG-13'),
        (4, 'R'),
        (5, 'NC-17')
        ) AS new_rating(id, name)
WHERE NOT EXISTS (
    SELECT 1 FROM mpa
    WHERE mpa.id = new_rating.id
);
