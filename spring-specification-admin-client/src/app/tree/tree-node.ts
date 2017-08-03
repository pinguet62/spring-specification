export interface TreeNode<T> {
    expanded: boolean;
    children: TreeNode<T>[];
    /** Additional data */
    data?: T;
}

export interface NodeMovedEvent<T> {
    node: TreeNode<T>;
    /** {@link TreeNode} where the {@link TreeNode} was moved. */
    parent: TreeNode<T>;
    /** Index inside all children of parent. */
    index: number;
}