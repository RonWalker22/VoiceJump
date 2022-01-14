
# Getting Started

## Intro to Actions

* `"post <target>"`: Places the cursor after a given target
* `"chunk <target>"`: Deletes a given target without moving the cursor
* `"take <target>"`: Selects a given target

See [more actions](#more-actions) for full list of actions.


## Intro to Targeting

### Example

* your editor before actions: ![ace action starting state](/docs/images/ace-action-targeting-state-1.png)
* spoken action: `post red`
  * targeting step 1; a VoiceJump Action
* potential targets: ![ace action starting state](/docs/images/ace-action-targeting-state-2.png)
* spoken action(Talon Action): `crunch` // trigger a tag
  * targeting step 2; a Talon Action
* your editor after actions: ![ace action end state](/docs/images/ace-action-targeting-state-3.png)


## More On Targeting

### Terms

You are not required to know or memorize these terms in order to use VoiceJump. But, it might help to look them over. These terms 
are used to drill down on precise language while discussing VoiceJump in issues, discussion, and commit messages. 


#### Target

> A **target** is a token in your code.

The token could be a word inside a string, variable identifier, class, function, block, parameter, and so on. There are many types see the 
full list [here](#token-types). 
#### Selected target
> A **selected target** is the token an action will be performed on.
#### Potential targets 
> **Potential targets** are the tokens that could become selected target.
#### Targeting
> **Targeting** is the process of acquiring a target.
#### Tags
> **Tags** are helper icons that map one or more letters of the talon alphabet(key) to a potential target(value).

Once you trigger a tag by calling its key, its value becomes the selected target. Tags only appear during "manual targeting". 
During "automatic targeting", this "targeting step" is skipped.  

#### Misfire
> A **misfire** occurs when you attempt to trigger a nonexistent tag. 

This can happen when you call a key that doesn't correspond to any tags.

#### Targeting step
> A **targeting step** is either a VoiceJump action or a talon action that takes place during targeting.
#### Manual targeting
> **Manual targeting** requires two or more steps to acquire a target.

 Manual targeting involves two steps the majority of the time. However, more steps are required for complex and or compound 
 acquisitions.

#### Automatic targeting
> **Automatic targeting** requires one step to acquire a target.

We attempt to maximize the opportunities for automatic targeting by limiting the targeting search area to the visible editor(with a few 
exceptions) and forcing the search query to begin at the start of a word/identifier. If you discover other opportunities, let us know.

## More Actions

- [x] `"take <target>"`: selects a given target
- [x] `"pre <target>"`: places the cursor before a given target
- [x] `"post <target>"`: places the cursor after a given target
- [x] `"chunk <target>"`: deletes a target without moving the cursor
- [ ] `"bring <target>"`: inserts a given target at the cursor position
- [ ] `"bring <target one> to <target two>"`: replaces target two with target one
- [ ] `"copy <target>"`: copies a given target

More actions coming...


## Visual Action Feedback

* cursor changes color and shape(block)
  * during targeting & misfire
