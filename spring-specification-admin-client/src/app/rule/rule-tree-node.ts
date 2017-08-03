import {TreeNode} from "primeng/primeng";
import {Rule} from "./rule";

export interface RuleDataTreeNode {
    rule?: Rule;
    index?: number;
    first?: boolean;
    last?: boolean;
}

export interface RuleTreeNode extends TreeNode {
    // override type
    children?: RuleTreeNode[];
    data: RuleDataTreeNode;
}

export function convert(rule: Rule): RuleTreeNode {
    let ruleTreeNode: RuleTreeNode = <RuleTreeNode> {
        expanded: true, // TODO test
        icon: 'fa-folder-open',
        label: rule.key,
        data: <RuleDataTreeNode> { rule: rule },
        parent: null
    };

    if (rule.components !== null) {
        ruleTreeNode.children = rule.components.map(convert);

        // parent
        for (let ruleChild of ruleTreeNode.children)
            ruleChild.parent = ruleTreeNode;

        // index & first & last
        let size: number = ruleTreeNode.children.length;
        for (let i: number = 0; i < size; i++) {
            let ruleChild: TreeNode = ruleTreeNode.children[i];
            ruleChild.data.index = i;
            ruleChild.data.first = (i === 0);
            ruleChild.data.last = (i === size-1);
        }
    }

    return ruleTreeNode;
}