package pacman;

import java.util.*;
import java.util.concurrent.ThreadLocalRandom;

public class AStrategy implements Strategy {
	// https://fr.wikipedia.org/wiki/Algorithme_A*

	Agent agent;
	PositionAgent target;
	
	PacmanGame world;
	
	//Dernier arrivé premier sorti
	Stack<AgentAction> pile;
	
	boolean isSearching;
	
	public AStrategy (Agent agent) {
		this.agent=agent;
		isSearching=true;
	}
	public AStrategy (Agent agent, PositionAgent target) {
		this.agent=agent;
		this.target=target;
		isSearching=false;
	}
	
	
	@Override
	public AgentAction action(PacmanGame m) {
		//if(agent instanceof Ghost) {
		//	int i = ThreadLocalRandom.current().nextInt(0,4);

		//	int j = (i+1)%4;
		//	AgentAction action = new AgentAction(j);
		//	while(!PacmanGame.detectionIncoherenceDeplacement(this.agent, action) && j != i){
		//		action = new AgentAction(j);
		//		j = (j+1)%4;
		//	}
		//	return action;
		//}

		this.world = m;
		
		if(isSearching) {
			for(Agent a: m.getAgents()) {
				if(a instanceof Pacman) {
					target = a.getCoord();
				}
			}
		}
		System.out.println("AEtoile");

		pile = new Stack<AgentAction>();
		AStar(agent.getCoord(), this.target);
		if(pile.size()==0) {
			return new AgentAction(4);
		}
		
		return pile.pop();
	}

	// Fonction cheminPlusCourt(g:Graphe, objectif:Nœud, depart:Nœud)
	// closedList = File()
	// openList = FilePrioritaire(comparateur=compare2Noeuds)
	// openList.ajouter(depart)
	// tant que openList n'est pas vide
	// 	u = openList.depiler()
	// 	si u.x == objectif.x et u.y == objectif.y
	// 		reconstituerChemin(u)
	// 		terminer le programme
	// 	pour chaque voisin v de u dans g
	// 		si non(v existe dans closedList ou v existe dans openList avec un coût inférieur)
	// 			 v.cout = u.cout +1 
	// 			 v.heuristique = v.cout + distanceAB([v.x, v.y], [objectif.x, objectif.y])
	// 			 openList.ajouter(v)
	// 	closedList.ajouter(u)
	// terminer le programme (avec erreur)

	private void AStar (PositionAgent _xyStart, PositionAgent target) {
		PositionAgent xy0 = _xyStart; xy0.setDir(0);
		PositionAgent target0 = target; target0.setDir(0);
		Queue<AStarNoeud> closed = new LinkedList<AStarNoeud>();
		Queue<AStarNoeud> open = new PriorityQueue<AStarNoeud>(100, new AStarNoeudComparator());
		//Noeud de départ
		AStarNoeud s = new AStarNoeud(xy0, null, null);
		open.add(s);
		while(!open.isEmpty()) {
			AStarNoeud u = open.poll();
			if(u.getPosition(target0)) {
				getpath(u);
				return;
			}
			for(int i=0;i<4;i++) {
				AgentAction a = new AgentAction(i);
				if(PacmanGame.detectionIncoherenceDeplacement(u.pos, a)) {
					int x = u.pos.getX()+a.get_vx();
					int y = u.pos.getY()+a.get_vy();
					PositionAgent p = new PositionAgent(x,y,0);
					if(!inQueue(p,open) && !inQueue(p,closed)) {
						AStarNoeud n = new AStarNoeud(p,u,a);
						open.add(n);
					}
				}
			}
			// Liste chainée des noeuds clos
			closed.add(u);
		}
	}
	
	
	private boolean inQueue(PositionAgent p, Queue<AStarNoeud> q) {
		Queue<AStarNoeud> l = q;
		for(AStarNoeud n : l) {
			if(n.getPosition(p)) return true;
		}
		return false;
	}
	
	private void getpath(AStarNoeud u) {
		if(u.nodesPassage != null) {
			pile.push(u.nodesPassage);	
			getpath(u.nodeDepart);
		}
	}


	// Fonction compare2Noeuds(n1:Nœud, n2:Nœud)
	// si n1.heuristique < n2.heuristique 
	// 	retourner -1
	// ou si n1.heuristique  > n2.heuristique 
	// 	retourner 1
	// sinon
	// 	retourner -1
	static class AStarNoeudComparator implements Comparator<AStarNoeud>{

		@Override
		public int compare(AStarNoeud n0, AStarNoeud n1) {
			if(n0.h < n1.h) return -1;
			if(n0.h > n1.h) return 1;
			return 0;
		}
	}
	
	int distanceAB(PositionAgent a0, PositionAgent a1) {
		return Math.abs(a0.getX()-a1.getX()) + Math.abs(a0.getY() - a1.getY());
	}
	
	class AStarNoeud {
		PositionAgent pos;
		AStarNoeud nodeDepart;
		AgentAction nodesPassage;
		int coutTrajet, coutPoint, h;
		
		AStarNoeud(PositionAgent pos, AStarNoeud nodeDepart, AgentAction nodesPassage){
			this.pos = pos;
			this.nodeDepart = nodeDepart;
			this.nodesPassage = nodesPassage;
			if(nodeDepart==null || nodesPassage==null) coutTrajet = 0;
			else coutTrajet = nodeDepart.coutTrajet+1;
			coutPoint = distanceAB(pos,target);
			setHeuristique();
		}

		boolean getPosition(AStarNoeud _an) {
			return getPosition(_an.pos);
		}
		boolean getPosition(PositionAgent _pa) {
			return pos.equals(_pa);
		}

		void updatecoutTrajet(int _c) {
			this.coutTrajet = _c;
			setHeuristique();
		}
		
		void setHeuristique() {
			// Fonction d'évaluation f(n) = g(n) + h(n)
			this.h = this.coutTrajet + this.coutPoint;
		}
	}
}