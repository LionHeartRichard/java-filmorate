SELECT * FROM film WHERE film_id IN (SELECT film_id FROM film_person GROUP BY film_id ORDER BY COUNT(person_id) DESC LIMIT 10);
