(clear)

(deftemplate person "People in actuarial database"
    (slot age (default -1))
    (slot name )
    (slot gender))
    
(deftemplate oldest-male (slot name) (slot age))

(assert (person (gender Male) (name "Mitt Romney") (age 61) ))
(assert (person (name "Bob Smith") (age 34) (gender Male)))
(assert (person (gender Male) (name "Tom Smith") (age 32) ))
(assert (person (name "Mary Smith") (age 34) (gender Female)))
;;(assert (person  (name "George Bush") (gender Male)))

;;(assert (person (gender Female)))



(defrule oldest-male-rule
    ;student to complete this rule
     ?f_1 <- (person (gender Male) (age ?m_age) (name ?m_name))
     ?f_2 <- (oldest-male (age ?old_male))
    =>
    (if (> ?m_age ?old_male) then
        (retract ?f_2)
        ;;(retract ?f_1)
        (assert (oldest-male (age ?m_age) (name ?m_name)))
    )
)    

 
(defrule show-oldest-male
    ?f1 <- (done)
    (oldest-male (age ?old_male) (name ?n))
    ; complete this rule which fires when other rules has finished.
    =>
    (printout t "oldest age is:  " ?old_male ?n crlf)
)    
     
     
(deffunction find-oldest-male ()
    ; student to complete this function
    ; involves asserts, runs and retracts
    (assert (oldest-male (age 0)))
    (run)
    (assert (done))
    (run)
)