let id = 0;
const nodesMap = {};
const markAllNodes = (node) => {		
	id++;

	if (node.nodeType === 1) {
		node.setAttribute("evinced_id", id);
		nodesMap[id] = node;
	}
	
	node.childNodes.forEach(child => {
		markAllNodes(child);
	});	
}

markAllNodes(document.body);

const evincedIdsStr = ';463;475;486;641;773;466;489;776;646;537;85';
const evincedIds = evincedIdsStr.split(';');
evincedIds.forEach(id => {
	let node = nodesMap[id];
	if (node) {
		node.style = node.style + ';border: 5px red solid';
	}
});