SELECT fp.film_id, COUNT(*) AS like_count 
FROM FILM_PERSON fp 
JOIN FILM f ON f.film_id = fp.film_id 
JOIN FILM_GENRE fg ON fg.film_id = f.film_id  
GROUP BY f.film_id 
ORDER BY like_count DESC LIMIT 100;

SELECT fp.film_id, COUNT(*) AS like_count 
FROM FILM_PERSON fp, FILM f, FILM_GENRE fg 
WHERE f.film_id = fp.film_id 
AND fg.film_id = f.film_id 
GROUP BY fp.film_id 
ORDER BY like_count DESC 
LIMIT 100;

SELECT fp.film_id, COUNT(*) AS like_count 
FROM FILM_PERSON fp, FILM f, FILM_GENRE fg 
WHERE f.film_id = fp.film_id AND fg.film_id = f.film_id  
AND YEAR(f.release_date) = 1999 
GROUP BY fp.film_id 
ORDER BY like_count DESC 
LIMIT 10;

SELECT fp.film_id, COUNT(*) AS like_count 
FROM FILM_PERSON fp, FILM f, FILM_GENRE fg 
WHERE f.film_id = fp.film_id 
AND fg.film_id = f.film_id  
AND fg.genre_id = 2 
AND YEAR(f.release_date) = 2000 
GROUP BY fp.film_id 
ORDER BY like_count DESC 
LIMIT 10;

SELECT * 
FROM film 
WHERE film_id IN (
	SELECT film_id 
	FROM film_person 
	GROUP BY film_id ORDER BY COUNT(person_id) DESC LIMIT 10
);

SELECT * FROM film WHERE film_id NOT IN (3,2);

SELECT COUNT(*) AS count_rows FROM film;

SELECT * FROM FILM_PERSON fp;

SELECT * FROM FILM f;