(clear)

(deftemplate person "People in actuarial database"
    (slot age)
    (slot name)
    (slot gender))


(assert (person (name "Bob Smith") (age 34) (gender Male)))
(assert (person (gender Male) (name "Tom Smith") (age 32) (gender Male)))
(assert (person (name "Mary Smith") (age 34) (gender Female)))
(assert (person (gender Female) (age 66)))

(defglobal ?*count* = 0)
(defglobal ?*agemin* = 1000)
(defrule male-ages
    (person (name ?n) (age ?a) (gender Male) )
    =>
    (printout t ?n " is " ?a " years old " crlf))
(defrule male-counted 
	(person (gender Male))
	=>
	(bind  ?*count* (+ 1 ?*count*))
)

(deffunction show-male-counted ()
	(printout t "amount: " ?*count* " Males "crlf)
)
(defrule youngest 
	(person (age ?x))
	=>
	(if (< ?x ?*agemin*)
	then
	(bind ?*agemin* ?x)
	)
)

(deffunction show-youngest ()
	(printout t "youngest: " ?*agemin* crlf)
)
(run)

(show-male-counted)
(show-youngest)