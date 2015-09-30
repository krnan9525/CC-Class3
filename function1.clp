(clear)
(bind ?ll (create$ cd1 cd2 movie1 movie2 track1))

(deffunction count-list (?list)
	(bind ?resault 0)
	(foreach ?x ?list
		(bind ?resault (+ 1 ?resault))
	)
	(return ?resault)
)
(bind ?res (count-list ?ll))
(printout t "total amount is  " ?res crlf)

(bind ?valu (create$ 1 23 3 4 5 6 55 78))
(deffunction cal-max (?list)
	(bind ?resault 0)
	(foreach ?x ?list
		(if (> ?x ?resault)
		then
		(bind ?resault ?x))
	)
	(return ?resault)
)
(bind ?res (cal-max ?valu))
(printout t "the max one is  " ?res crlf)

(deffunction cal-average (?list)
(bind ?resault 0)
	(foreach ?x ?list
		(bind ?resault (+ ?resault ?x))
	)	
	(bind ?res (count-list ?ll))
	(return (/ ?resault ?res))
)

(bind ?res (cal-average ?valu))
(printout t "the max one is  " ?res crlf)

(bind ?lll (create$ 5.50 milk 4.00 butter 6.70 eggs 2.30 bread 4.55 muffins))
(bind ?res (count-list ?lll))
(printout t "total items is  " (/ ?res 2) crlf)

(deffunction calculate-cost (?list)
(bind ?resault 0)
	(foreach ?x ?list
	(if (numberp ?x)
		then
		(bind ?resault (+ ?resault ?x))
	)
	)
	(return ?resault)
)
(bind ?res (calculate-cost ?lll))
(printout t "average cost is  "  ?res crlf)

(deffunction cal-five (?list)
(bind ?symble 0)
(bind ?resault 0)
	(foreach ?x ?list
		(if (= ?symble 1)
		then
		
			(bind ?symble 0)
			(printout t " " ?x crlf)
		
		else
	(if (numberp ?x)
		then
		(if (> ?x 5 ) then
			
				(printout t ?x " is for:  ")
				(bind ?symble 1)
			
		)	
	))
	)
)
(cal-five ?lll)