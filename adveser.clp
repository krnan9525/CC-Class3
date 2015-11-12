(reset)

(defrule go-rule-1
    =>
    (printout t "What sort of environment is a trainee dealing with on the job? " crlf)
    (bind ?e (read))
    (assert (environment ?e))
    (printout t "What sort of job is the trainee do? " crlf)
    (bind ?e (read))
    (assert (job ?e))
    (printout t "Is the feedback required or not? (yes / no)" crlf)
    (bind ?e (read))
    (assert (feedback ?e))
    (run)
)



(defrule rule_1
    (environment papers | manuals | documents | textbooks)
     =>
    (assert (stimulus-situation verbal))
)

(defrule rule_2
    (environment pictures | illustrations | photographs | diagrams)
     =>
    (assert (stimulus-situation visual))
)


(defrule rule_3
    (environment machines | buildings | tools)
     =>
    (assert (stimulus-situation 'physical objects'))
)


(defrule rule_4
    (environment numbers | formulas | cumputer programs)
     =>
    (assert (stimulus-situation symbolic))
)


(defrule rule_5
    (job lecturing  | advising | counselling)
     =>
    (assert (stimulus-response oral))
)


(defrule rule_6
    (job building  | repairing | troubleshooting)
     =>
    (assert (stimulus-response 'hands on'))
)


(defrule rule_7
    (job typing  | writing | drawing)
     =>
    (assert (stimulus-response documented))
)


(defrule rule_8
    (job evlauating | reasoning | investigating)
     =>
    (assert (stimulus-response analytical))
)

(defrule rule_9
    (and (stimulus-situation 'physical objects')
         (stimulus-response 'hands on')
         (feedback yes)
    )
    =>
    (assert (medium workshop))
)


(defrule rule_10
    (and (stimulus-situation symbolic)
         (stimulus-response analytical)
         (feedback yes)
    )
    =>
    (assert (medium 'lecture-tutorial'))
)


(defrule rule_11
    (and (stimulus-situation visual)
         (stimulus-response oral)
         (feedback no)
    )
    =>
    (assert (medium 'lecture-tutorial'))
)


(defrule rule_12
    (and (stimulus-situation visual)
         (stimulus-response analytical)
         (feedback yes)
    )
    =>
    (assert (medium 'lecture-tutorial'))
)


(defrule rule_13
    (and (stimulus-situation visual)
         (stimulus-response oral)
         (feedback yes)
    )
    =>
    (assert (medium 'role-play exercises'))
)


(defrule rule_14
    (and (stimulus-situation verbal)
         (stimulus-response documented)
         (feedback yes)
    )
    =>
    (assert (medium videocassette))
)