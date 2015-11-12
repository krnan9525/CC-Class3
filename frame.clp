; -*- clips -*-

;; **********************************************************************
;;                           Frame.clp
;;
;; A nifty example of building a a Swing GUI using jess reflection.
;; Using this package, we can create java objects, call their methods,
;; access their fields, and respond to GUI events.
;; You can therefore build an entire GUI application without actually
;; writing any Java code!
;;
;; **********************************************************************

;; ******************************
;; Declarations

(import javax.swing.*)
;; Explicit import so we get JFrame.EXIT_ON_CLOSE
(import javax.swing.JFrame) 
(import java.awt.event.ActionListener)
(import java.awt.BorderLayout)
(import java.awt.Color)

;; ******************************
;; DEFGLOBALS

(defglobal ?*f* = 0)
(defglobal ?*c* = 0)
(defglobal ?*d* = 0)
(defglobal ?*e* = 0)

(defglobal ?*m* = 0)
(defglobal ?*envi* = 0)

;; ******************************
;; DEFFUNCTIONS

(deffunction create-frame ()
  (bind ?*f* (new JFrame "Jess Reflection Demo"))
  (bind ?*c* (?*f* getContentPane)) 
  (set ?*c* background (Color.magenta)))


(deffunction add-widgets ()
  (?*c* add (new JLabel "This is: ") (BorderLayout.CENTER))
  (bind ?*m* (new JComboBox))
  (bind ?*d* (new JComboBox))
  (bind ?*e* (new JComboBox))
  (?*m* addItem "paper")
  (?*m* addItem "tools")
  (?*m* addItem "buildings")
  (?*m* addItem "numbers")
  (?*c* add ?*m* (BorderLayout.SOUTH))
  (bind ?*envi* ?*m* selectedItem)
  (assert (environment ?*envi*))  
  (?*e* addItem "paper")
  (?*e* addItem "tools")
  (?*e* addItem "buildings")
  (?*e* addItem "numbers")
  (?*c* add ?*e* (BorderLayout.NORTH))
  (bind ?*envi* ?*m* selectedItem)
  (assert (environment ?*envi*))
  (?*d* addItem "paper")
  (?*d* addItem "tools")
  (?*d* addItem "buildings")
  (?*d* addItem "numbers")
  (?*c* add ?*d* )
  (bind ?*envi* ?*d* selectedItem )
  (assert (environment ?*envi*)))


(deffunction add-behaviours ()
  (?*f* setDefaultCloseOperation (JFrame.EXIT_ON_CLOSE))
                            
  (?*m* addActionListener (implement ActionListener using
    (lambda (?name ?event)
      (printout t "You chose: " (get ?*m* selectedItem) crlf)))))

(deffunction show-frame ()
  ;(?*f* pack)
    (?*f* setSize 200 300)
  (?*f* setVisible TRUE))


;; ******************************
;; Run the program

(defrule init-rule
    (initial-fact)
    =>
    (create-frame)
    (add-widgets)
    (add-behaviours)
    (show-frame))

(reset)
(run)

;;test now 2
;;howareyougit 
;;123131469564131345561
;;mmmark
;sdfsdfjklsdfh