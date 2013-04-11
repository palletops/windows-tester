(use 'pallet.repl) (use-pallet)

;; define the compute provider
(def vmfest (compute-service :vmfest))

;; do we have existing nodes?
(show-nodes vmfest)

;; create a group named "tester" that will run debian and authorize
;; our ssh credentials on the node.
(use 'pallet.crate.automated-admin-user)
(def tester (group-spec "tester"
                        :node-spec {:image {:image-id :debian-6.0.2.1-64bit-v0.3}}
                        :extends [with-automated-admin-user]))

;; create one instance in the "tester" group
(converge {tester 1} :compute vmfest)

;; check the IP address of our node
(show-group vmfest "tester")

;; ssh into the primary-ip of the tester group. Should work without
;; password.
;; $ ssh <ip>

;; destroy all the instances in the "tester" group
(converge {tester 0} :compute vmfest)