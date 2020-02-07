import {TreeNode} from '../tree/tree-node';
import {RuleComponent} from './rule-component';

export interface RuleComponentDataTreeNode {
    ruleComponent?: RuleComponent;
    index?: number;
    first?: boolean;
    last?: boolean;
    parent?: TreeNode<RuleComponentDataTreeNode>;
}

export function convert(ruleComponent: RuleComponent): TreeNode<RuleComponentDataTreeNode> {
    const treeNode: TreeNode<RuleComponentDataTreeNode> = <TreeNode<RuleComponentDataTreeNode>> {
        expanded: true, // TODO test
        acceptChildren: ['fr.pinguet62.springspecification.core.api.AndRule', 'fr.pinguet62.springspecification.core.api.OrRule', 'fr.pinguet62.springspecification.core.api.NotRule'].includes(ruleComponent.key),
        children: [],
        data: <RuleComponentDataTreeNode> {
            ruleComponent: ruleComponent,
            parent: null
        }
    };

    if (ruleComponent.components !== null) {
        treeNode.children = ruleComponent.components.map(convert);

        // parent
        for (const ruleChild of treeNode.children) {
            ruleChild.data.parent = treeNode;
        }

        // index & first & last
        const size = treeNode.children.length;
        for (let i = 0; i < size; i++) {
            const ruleChild: TreeNode<RuleComponentDataTreeNode> = treeNode.children[i];
            ruleChild.data.index = i;
            ruleChild.data.first = (i === 0);
            ruleChild.data.last = (i === size - 1);
        }
    }

    return treeNode;
}
