package tutorial;

import com.amalgamasimulation.utils.container.BiMap;

public class Mapping {
	public BiMap<tutorial.scenario.Asset, tutorial.model.Asset> assetsMap = new BiMap<>();
	public BiMap<tutorial.scenario.Node, tutorial.model.Node> nodesMap = new BiMap<>();
	public BiMap<tutorial.scenario.Arc, tutorial.model.Arc> forwardArcsMap = new BiMap<>();
}
