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

const evincedIdsStr = ';808;492;494;593;473;572;477;598;675;654;676;479;611;776;798;634;459;712;658;758;617;55;59;461;780;464;488;565;642;664;621;764;786;723;9;724;802;63;706';
const evincedIds = evincedIdsStr.split(';');
evincedIds.forEach(id => {
	let node = nodesMap[id];
	if (node) {
		node.style = node.style + ';border: 5px red solid';
	}
});