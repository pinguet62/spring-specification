import {TreeNode} from "../tree/tree-node";
import {Rule} from "./rule";

export interface RuleDataTreeNode {
    rule?: Rule;
    index?: number;
    first?: boolean;
    last?: boolean;
    parent: TreeNode<RuleDataTreeNode>
}

export function convert(rule: Rule): TreeNode<RuleDataTreeNode> {
    let treeNode: TreeNode<RuleDataTreeNode> = <TreeNode<RuleDataTreeNode>> {
        expanded: true, // TODO test
        acceptChildren: ['andRule', 'orRule', 'notRule'].includes(rule.key),
        children: [],
        data: <RuleDataTreeNode> {
            rule: rule,
            parent: null
        }
    };

    if (rule.components !== null) {
        treeNode.children = rule.components.map(convert);

        // parent
        for (let ruleChild of treeNode.children)
            ruleChild.data.parent = treeNode;

        // index & first & last
        let size: number = treeNode.children.length;
        for (let i: number = 0; i < size; i++) {
            let ruleChild: TreeNode<RuleDataTreeNode> = treeNode.children[i];
            ruleChild.data.index = i;
            ruleChild.data.first = (i === 0);
            ruleChild.data.last = (i === size-1);
        }
    }

    return treeNode;
}